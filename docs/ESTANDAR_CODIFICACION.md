# Estándar de codificación aplicado

- Base package: `com.banquito.core.admin`.
- Java 21 LTS, Spring Boot 4.0.6, Maven.
- Entidades JPA con `@Entity`, `@Table`, `@Id`, `@GeneratedValue` y `@Column` explícito.
- Lombok solo para `@Getter` y `@Setter` en entidades.
- No se usa `@Data` en entidades JPA.
- `equals()` y `hashCode()` manuales por ID.
- `toString()` manual y seguro.
- Enums con sufijo `Enum` y `@Enumerated(EnumType.STRING)`.
- `@Version` en entidades mutables.
- Repositorios como interfaces que extienden `JpaRepository`.
- Controladores delegan reglas a servicios.
- No existen relaciones JPA cruzadas con otros microservicios.
