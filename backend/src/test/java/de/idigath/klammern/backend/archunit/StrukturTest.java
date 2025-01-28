package de.idigath.klammern.backend.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import de.idigath.klammern.backend.config.PostgresContainer;
import de.idigath.klammern.backend.service.spiel.PartieImpl;
import de.idigath.klammern.backend.service.spiel.phase.Phase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SpringBootTest
public class StrukturTest extends PostgresContainer {

    @Test
    public void phasenAufruf_nurVonRundeAus_ok() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("de.idigath.klammern.backend");
        classes()
                .that()
                .implement(Phase.class)
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAPackage("de.idigath.klammern.backend.service.spiel..")
                .orShould()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAPackage("de.idigath.klammern.backend.model..")
                .check(importedClasses);

        classes()
                .that()
                .implement(Phase.class)
                .should().onlyBeAccessed()
                .byClassesThat().areNotAssignableTo(PartieImpl.class) // Запрещаем доступ от ClassB
                .check(importedClasses);
    }


}
