import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<List<Integer>> enderecoIPBinario = EnderecoIPOuMascara();
        List<List<Integer>> mascaraBinario = EnderecoIPOuMascara();
        List<List<Integer>> enderecoDaRedeBinario = enderecoDaRedeEmBinario(enderecoIPBinario, mascaraBinario);
        List<Integer> enderecoDeRede = metodoParaObterEnderecoDeRede(enderecoDaRedeBinario);


        System.out.println(enderecoIPBinario);
        System.out.println(mascaraBinario);
        System.out.println(enderecoDaRedeBinario);
        System.out.println(enderecoDeRede);


        broadcastExtrator(enderecoDaRedeBinario, mascaraBinario);


    }

    // 172.25.114.250
// 255.255.0.0

    private static void broadcastExtrator(List<List<Integer>> enderecoDaRedeBinario, List<List<Integer>> mascaraBinario) {
        ArrayList<Integer> posicao = new ArrayList<>();
        for (int i = 0; i < enderecoDaRedeBinario.size(); i++) {
            int count = 0;
            for (int j = 0; j < enderecoDaRedeBinario.get(i).size(); j++) {
                if (enderecoDaRedeBinario.get(i).get(j).equals(1)) {
                    count++;
                }
            }
            if (count == 0) {
                posicao.add(i);
            }
        }

        System.out.println(posicao);

    }


    private static List<Integer> metodoParaObterEnderecoDeRede(List<List<Integer>> enderecoDaRedeBinario) {
        List<List<Integer>> converterDaBase2ParaDecimal = new ArrayList<>();
        for (int i = 0; i < enderecoDaRedeBinario.size(); i++) {
            converterDaBase2ParaDecimal.add(new ArrayList<>());
            int baseDois = 1;
            for (int j = 7; j >= 0; j--) {
                if (j == 7) {
                    if (enderecoDaRedeBinario.get(i).get(j).equals(0)) {
                        converterDaBase2ParaDecimal.get(i).add(0);
                    } else {
                        converterDaBase2ParaDecimal.get(i).add(1);
                    }
                } else {
                    baseDois = baseDois * 2;
                    if (enderecoDaRedeBinario.get(i).get(j).equals(1)) converterDaBase2ParaDecimal.get(i).add(baseDois);
                }
            }
        }
        List<Integer> enderecoDeRedeEmDecimal = new ArrayList<>();
        for (List<Integer> integers : converterDaBase2ParaDecimal) {
            int soma = 0;
            for (Integer integer : integers) {
                soma += integer;
            }
            enderecoDeRedeEmDecimal.add(soma);
        }

        return enderecoDeRedeEmDecimal;
    }


    private static List<List<Integer>> enderecoDaRedeEmBinario(List<List<Integer>> enderecoIPBinario, List<List<Integer>> mascaraBinario) {
        List<List<Integer>> resultado = new ArrayList<>();
        for (int i = 0; i < enderecoIPBinario.size(); i++) {
            resultado.add(new ArrayList<>());
            for (int j = 0; j < enderecoIPBinario.get(i).size(); j++) {
                if (mascaraBinario.get(i).get(j).equals(1) && enderecoIPBinario.get(i).get(j).equals(1)) {
                    resultado.get(i).add(1);
                } else {
                    resultado.get(i).add(0);
                }
            }
        }
        return resultado;
    }

    private static List<List<Integer>> EnderecoIPOuMascara() {
        Scanner leitor = new Scanner(System.in);
        List<List<Integer>> enderecoIPBinarioOuMascara = new ArrayList<>();
        while (enderecoIPBinarioOuMascara.size() != 4) {
            System.out.println("Calculadora binária");
            System.out.print("Digite o endereço IP ou Mascara: ");
            String enderecoIP = leitor.next();

            String[] split = enderecoIP.split("\\.");
            List<Integer> parteDosEnderecosDecimal = new ArrayList<>();

            for (String s : split) {
                parteDosEnderecosDecimal.add(Integer.parseInt(s));
            }


            for (Integer i : parteDosEnderecosDecimal) {
                List<Integer> parteDosEnderecosEmBinario = new ArrayList<>();
                while (i >= 2) {
                    parteDosEnderecosEmBinario.add(i % 2);
                    i = i / 2;
                }
                parteDosEnderecosEmBinario.add(i);

                for (int j = parteDosEnderecosEmBinario.size(); j < 8; j++) {
                    parteDosEnderecosEmBinario.add(0);
                }

                enderecoIPBinarioOuMascara.add(parteDosEnderecosEmBinario.reversed());

            }
        }
        return enderecoIPBinarioOuMascara;
    }
}