package projetPFA.gestionFonct;


import jakarta.persistence.*;

@Entity
@Table

public class test {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   private String name;
    private String age;




}
