# JPA Mini-Beispiele

**Konventionen:**

- `fetch = LAZY` explizit auf `@OneToOne`/`@ManyToOne`.
- Lifecycle-Operationen (`cascade`, `orphanRemoval`) setzt du **dort, wo das Elternobjekt über seine Kinder entscheidet**. In der Praxis meist auf der **1‑Seite mit der Collection** (zB. `Order.items`).
- Owner Seite = `@JoinColumn`/`@JoinTable`. (FK wird hier gespeichert.)
- Inverse Seite = `mappedBy`.
- nullable = false auf FK-Spalten, wenn Beziehung immer existieren muss.
- unique = true auf FK-Spalten bei 1:1 Beziehungen.
- Kein unique=true auf @ManyToOne, sonst machst du aus 1:n fälschlich 1:1.

---

## 1) 1:1 unidirektional (Owner setzt FK)

```java
import jakarta.persistence.*;

@Entity
class Person {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "passport_id", nullable = false, unique = true)
  Passport passport; // Owner Seite, FK in PERSON.PASSPORT_ID

  public Person() {}
}

@Entity
class Passport {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String number;

  public Passport() {}
}
```

*FK in **`Person`**; **`unique = true`** erzwingt echte 1:1. **`orphanRemoval`** löscht **`Passport`**, wenn getrennt (Private Ownership).*

---

## 2) 1:1 bidirektional (Owner + inverse Seite)

```java
import jakarta.persistence.*;

@Entity
class UserProfile {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "avatar_id", nullable = false, unique = true)
  Avatar avatar; // Owner Seite (FK in USER_PROFILE.AVATAR_ID)

  public UserProfile() {}
}

@Entity
class Avatar {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String url;

  @OneToOne(mappedBy = "avatar", fetch = FetchType.LAZY)
  UserProfile profile; // Inverse Seite

  public Avatar() {}
}
```

---

## 3) 1\:n bidirektional (empfohlen)

```java
import jakarta.persistence.*;
import java.util.*;

@Entity
class Order {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
             cascade = CascadeType.ALL, orphanRemoval = true)
  List<OrderItem> items = new ArrayList<>(); // Inverse Seite

  public Order() {}

  // Helper: beide Seiten pflegen
  public void addItem(OrderItem i) {
    if (i == null) return;
    if (!items.contains(i)) { items.add(i); i.setOrder(this); }
  }
  public void removeItem(OrderItem i) {
    if (i == null) return;
    if (items.remove(i)) { i.setOrder(null); } // orphanRemoval => DELETE von OrderItem
  }
}

@Entity
class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", nullable = false)
  Order order; // Owner Seite (FK in ORDER_ITEM.ORDER_ID)

  public OrderItem() {}
  void setOrder(Order o) { this.order = o; }
}
```

*Owner = n‑Seite. Parent steuert Lifecycle (**`cascade`**, **`orphanRemoval`**).*

---

## 4) 1\:n unidirektional (portabel über Join-Tabelle)

```java
import jakarta.persistence.*;
import java.util.*;

@Entity
class Team {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinTable(
    name = "team_member",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "member_id")
  )
  List<Member> members = new ArrayList<>();

  public Team() {}

  public void addMember(Member m) { if (m != null && !members.contains(m)) members.add(m); }
  public void removeMember(Member m) { members.remove(m); } // orphanRemoval => DELETE von Member
}

@Entity
class Member {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String name;
  public Member() {}
}
```

*Standard-JPA unidirektional 1**:n** ist portabel mit Join-Tabelle (keine Backref nötig).

---

## 5) 1\:n unidirektional — **Owner = n‑Seite**, keine Liste/Referenz auf der 1‑Seite

```java
import jakarta.persistence.*;

@Entity
class BlogPost {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String title;

  public BlogPost() {}
}

@Entity
class Comment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String text;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "post_id", nullable = false)
  BlogPost post; // OWNER FK in COMMENT.POST_ID; keine Backref in BlogPost

  public Comment() {}

  public void setPost(BlogPost post) { this.post = post; }
}
```

**Hinweise**

- Owner = n‑Seite (`Comment`) schreibt den FK.
- Parent (`BlogPost`) hat **keine** Collection – unidirektional vom Child zum Parent.
- Auf `@ManyToOne` meist **keine **``**‑Cascade**; optional `PERSIST`/`MERGE`:
  ```java
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  ```
- **Kein ** auf `@ManyToOne`. Orphans ggf. per Businesslogik löschen – oder Beziehung bidirektional modellieren.

---

## 6) Inheritance – (SINGLE\_TABLE)

```java
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "pm_type", length = 10)
abstract class PaymentMethod {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String alias;

  public PaymentMethod() {}
}

@Entity
@DiscriminatorValue("CC")
class CreditCard extends PaymentMethod {
  String number;
  public CreditCard() {}
}

@Entity
@DiscriminatorValue("PP")
class Paypal extends PaymentMethod {
  String email;
  public Paypal() {}
}
```

*SINGLE\_TABLE ist schnell & simpel. Alternativen: **`JOINED`** (normalisiert, mehr JOINs), **`TABLE_PER_CLASS`** (selten sinnvoll).*
