import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String teste;
        teste = leitor.next();
        String[] split = teste.split("\\.");
        List<Integer> num2 = new ArrayList<>();

        for (String s : split) {
            num2.add(Integer.parseInt(s));
        }

        System.out.println(num2);


    }
}
