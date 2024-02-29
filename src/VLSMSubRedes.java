import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VLSMSubRedes {
    public static void main(String[] args) {
        List<List<Integer>> enderecoIP = obterEnderecoIP();
        List<Integer> requisitosHosts = obterRequisitosHosts();
        List<List<Integer>> binariosHosts = converterParaBinario(requisitosHosts);
        List<Integer> bitsNecessarios = calcularBitsNecessarios(binariosHosts);
        List<List<List<Integer>>> informacoesSubRedes = extrairInformacoesSubRedes(requisitosHosts, enderecoIP, bitsNecessarios);

        if (!informacoesSubRedes.isEmpty()) {
            System.out.println("Endereço IP: " + enderecoIP);
            System.out.println("Requisitos de Hosts: " + requisitosHosts);
            System.out.println("Binários dos Hosts: " + binariosHosts);
            System.out.println("Bits Necessários: " + bitsNecessarios);
            imprimirSubRedes(informacoesSubRedes);
        }

        for (int i = 0; i < requisitosHosts.size(); i++) {

        for (int j = 0; j < enderecoIP.getFirst().size(); j++) {
            if (enderecoIP.getFirst().get(i).equals(enderecoIP.getFirst().getLast())) {

            }
        }
        }
    }

    private static void imprimirSubRedes(List<List<List<Integer>>> informacoesSubRedes) {
        for (List<List<Integer>> subRedes : informacoesSubRedes) {
            System.out.println("Informações da Sub-Rede: " + subRedes);
        }
    }

    private static List<Integer> calcularBitsNecessarios(List<List<Integer>> binarios) {
        List<Integer> qtdBitsOtimizado = new ArrayList<>();
        for (List<Integer> binario : binarios) {
            qtdBitsOtimizado.add(32 - binario.size());
        }
        return qtdBitsOtimizado;
    }

    private static List<List<Integer>> converterParaBinario(List<Integer> requisitosHosts) {
        List<List<Integer>> binarios = new ArrayList<>();
        for (Integer requisito : requisitosHosts) {
            List<Integer> binario = new ArrayList<>();
            while (requisito >= 2) {
                binario.add(requisito % 2);
                requisito /= 2;
            }
            binario.add(requisito);
            binarios.add(inverterLista(binario));
        }
        return binarios;
    }

    private static List<Integer> inverterLista(List<Integer> lista) {
        List<Integer> invertida = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--) {
            invertida.add(lista.get(i));
        }
        return invertida;
    }

    private static List<List<List<Integer>>> extrairInformacoesSubRedes(List<Integer> requisitosHosts, List<List<Integer>> enderecoIP, List<Integer> bitsNecessarios) {
        List<List<List<Integer>>> informacoesSubRedes = new ArrayList<>();
        int incremento = 0;
        for (int i = 0; i < requisitosHosts.size(); i++) {
            int contador = 0;
            incremento++;
            while (requisitosHosts.get(i) != contador) {
                List<List<Integer>> enderecoMascara = new ArrayList<>();
                List<Integer> enderecoHost = new ArrayList<>();
                for (int j = 0; j < enderecoIP.getFirst().size(); j++) {
                    if (enderecoIP.getFirst().size() - 1 == j) {
                        enderecoHost.add(incremento++);
                        enderecoMascara.add(enderecoHost);
                    } else {
                        enderecoHost.add(enderecoIP.getFirst().get(j));
                    }
                }
                List<Integer> mascara = new ArrayList<>();
                mascara.add(bitsNecessarios.get(i));
                enderecoMascara.add(mascara);
                informacoesSubRedes.add(enderecoMascara);

                ++contador;
            }
            incremento++;
        }
        if (incremento > 254) {
            System.out.println("Necessário Máscara com mais capacidade de bits para host");
            informacoesSubRedes.clear();
            return informacoesSubRedes;
        }
        return informacoesSubRedes;
    }

    private static List<Integer> obterRequisitosHosts() {
        Scanner leitor = new Scanner(System.in);
        List<Integer> requisitosHosts = new ArrayList<>();
        System.out.println("Digite a quantidade de roteadores necessários:");
        int qtdRoteadores = leitor.nextInt();
        System.out.println("Digite a quantidade de hosts necessários para cada roteador:");
        for (int i = 0; i < qtdRoteadores; i++) {
            System.out.print("Roteador " + (i + 1) + ": ");
            requisitosHosts.add(leitor.nextInt());
        }
        requisitosHosts.sort(Integer::compareTo);
        List<Integer> qtdBits = calcularBits(requisitosHosts);

        return inverterLista(qtdBits);
    }

    private static List<Integer> calcularBits(List<Integer> requisitosHosts) {
        List<Integer> qtdBits = new ArrayList<>();
        for (Integer requisito : requisitosHosts) {
            int bits = 1;
            while (bits <= requisito) {
                bits *= 2;
            }
            bits -= 2;
            qtdBits.add(bits);
        }
        return qtdBits;
    }

    private static List<List<Integer>> obterEnderecoIP() {
        System.out.print("Digite o endereço IP (formato: xxx.xxx.xxx.xxx/máscara): ");
        Scanner leitor = new Scanner(System.in);
        String endereco = leitor.next();
        List<List<Integer>> enderecoIP = new ArrayList<>();
        List<Integer> enderecoLista = new ArrayList<>();
        List<Integer> classeLista = new ArrayList<>();

        int indice = endereco.indexOf("/");
        String enderecoString = endereco.substring(0, indice);
        String classeString = endereco.substring(indice + 1);

        for (String octeto : enderecoString.split("\\.")) {
            enderecoLista.add(Integer.parseInt(octeto));
        }
        enderecoIP.add(enderecoLista);

        classeLista.add(Integer.parseInt(classeString));
        enderecoIP.add(classeLista);

        return enderecoIP;
    }
}
