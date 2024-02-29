import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VLSM {
    public static void main(String[] args) {

        int menu = 0;

        while (menu != 1){

        System.out.println("Calculadora binária");
        System.out.print("Digite o endereço IP: ");
        List<Integer> enderecoIP = dividirOctetosEmInteger();
        System.out.print("Digite a Máscara: ");
        List<Integer> mascaraDaRede = dividirOctetosEmInteger();

        List<List<Integer>> enderecoIPBinario = conversorOctetoParaBinario(enderecoIP);
        List<List<Integer>> mascaraBinario = conversorOctetoParaBinario(mascaraDaRede);
        List<List<Integer>> enderecoDaRedeBinario = enderecoDaRedeEmBinario(enderecoIPBinario, mascaraBinario);

        List<Integer> enderecoDeRede = extratorDecimalDosOctetos(enderecoDaRedeBinario);
        List<Integer> broadcast = broadcastExtrator(mascaraBinario, enderecoDeRede, enderecoDaRedeBinario);
        int bits = extratorBits(mascaraBinario);
        int hostsValidos = totalHostsValidos(bits);


        print(enderecoIP, mascaraDaRede, enderecoIPBinario, mascaraBinario, enderecoDaRedeBinario, enderecoDeRede, broadcast, bits, hostsValidos);
            System.out.println("Dique 1 para sair, senão dique qualquer número para continuar operando: ");
            Scanner leitor = new Scanner(System.in);
            menu = leitor.nextInt();
        }
    }

    private static void print(List<Integer> enderecoIP, List<Integer> mascaraDaRede, List<List<Integer>> enderecoIPBinario, List<List<Integer>> mascaraBinario, List<List<Integer>> enderecoDaRedeBinario, List<Integer> enderecoDeRede, List<Integer> broadcast, int bits, int hostsValidos) {
        System.out.println("------------------------------------");
        System.out.println("Endereço IP: " + enderecoIP);
        System.out.println("Máscara de Rede: " + mascaraDaRede);
        System.out.println("Endereço IP em Binário: " + enderecoIPBinario);
        System.out.println("Máscara em Binário: " + mascaraBinario);
        System.out.println("Endereço da Rede Binário: " + enderecoDaRedeBinario);
        System.out.println("Endereço de Rede: " + enderecoDeRede);
        System.out.println("Endereço de Broadcast da Rede: " + broadcast);
        System.out.println("Número Total de Bits de Host: " + bits);
        System.out.println("Número de Hosts: " + hostsValidos);
        System.out.println("------------------------------------");
    }



    private static List<Integer> dividirOctetosEmInteger() {
        Scanner leitor = new Scanner(System.in);
        String num = leitor.next();
        List<Integer> octetos = new ArrayList<>();
        for (String string : num.split("\\.")) {
            octetos.add(Integer.parseInt(string));
        }
        return octetos;
    }

    private static int extratorBits(List<List<Integer>> mascaraBinario) {
        int bitsHost = 0;
        for (List<Integer> externo : mascaraBinario) {
            for (Integer i : externo) {
                if (i.equals(0)) {
                    bitsHost++;
                }
            }
        }

        return bitsHost;
    }

    private static List<Integer> broadcastExtrator(List<List<Integer>> mascaraBinario, List<Integer> enderecoDeRede, List<List<Integer>> enderecoDaRedeBinario) {
        List<Integer> mascara = extratorDecimalDosOctetos(mascaraBinario);
        List<Integer> broadcast = new ArrayList<>();
        List<List<Integer>> broadcastBinario = new ArrayList<>();
        int count = 0;

        for (Integer i : mascara) {
            if (i.equals(255)) {
                count++;
            }
        }

        if (count == 1) {
            broadcast.add(enderecoDeRede.getFirst());
            return broadcastDecimal(mascaraBinario, enderecoDaRedeBinario, count, broadcastBinario, broadcast);


        } else if (count == 2) {
            for (int i = 0; i < 2; i++) {
                broadcast.add(enderecoDeRede.get(i));
            }
            return broadcastDecimal(mascaraBinario, enderecoDaRedeBinario, count, broadcastBinario, broadcast);
        } else {
            for (int i = 0; i < 3; i++) {
                broadcast.add(enderecoDeRede.get(i));
            }
            return broadcastDecimal(mascaraBinario, enderecoDaRedeBinario, count, broadcastBinario, broadcast);
        }
    }

    private static List<Integer> broadcastDecimal(List<List<Integer>> mascaraBinario, List<List<Integer>> enderecoDaRedeBinario, int count, List<List<Integer>> broadcastBinario, List<Integer> broadcast) {
        for (int i = count; i < mascaraBinario.size(); i++) {
            List<Integer> passador = new ArrayList<>();
            for (int j = 0; j < mascaraBinario.get(i).size(); j++) {
                if (enderecoDaRedeBinario.get(i).get(j).equals(0) && mascaraBinario.get(i).get(j).equals(0)) {
                    passador.add(1);
                } else {
                    passador.add(enderecoDaRedeBinario.get(i).get(j));
                }
            }
            broadcastBinario.add(passador);
        }
        broadcast.addAll(extratorDecimalDosOctetos(broadcastBinario));
        return broadcast;
    }

    private static int totalHostsValidos(int bits) {
        int totalHostsDisponivel = 1;
        for (int i = 0; i < bits; i++) {
            totalHostsDisponivel *= 2;
        }
        totalHostsDisponivel -= 2;

        return totalHostsDisponivel;
    }



    private static List<Integer> extratorDecimalDosOctetos(List<List<Integer>> enderecoDaRedeBinario) {
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

    private static List<List<Integer>> conversorOctetoParaBinario(List<Integer> parteDosEnderecosDecimal) {
        List<List<Integer>> octetos = new ArrayList<>();
        while (octetos.size() != 4) {

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

                octetos.add(parteDosEnderecosEmBinario.reversed());

            }
        }
        return octetos;
    }
}