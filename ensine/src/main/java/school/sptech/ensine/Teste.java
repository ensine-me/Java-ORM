package school.sptech.ensine;

import school.sptech.ensine.enumeration.DiasDaSemana;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Teste {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println("Current day of the week: " + DiasDaSemana.SEXTA.ordinal());
    }
}
