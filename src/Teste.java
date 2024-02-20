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

//      172.30.1.33
//      255.255.0.0

//      172.30.1.33
//      255.255.255.0

//      192.168.10.234
//      255.255.255.0
//
//      172.17.99.71
//      255.255.0.0
//
//      192.168.3.219
//      255.255.0.0

//      172.25.114.250
//      255.255.0.0

//    192.168.3.219
//    255.255.255.224
    }
}
