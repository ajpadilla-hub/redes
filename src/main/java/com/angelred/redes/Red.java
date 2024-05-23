package com.angelred.redes;

import java.util.Scanner;

/**
 *
 * @author angelred
 */
public class Red {

    public boolean DevolverDireccionRed() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce una ipv4");
        StringBuilder ipv4 = new StringBuilder();
        ipv4.append(scan.nextLine());
        this.ipv4ABin(ipv4);

        System.out.println("Introduce longitud de Prefijo de Red");
        String longitudPrefijoRed = scan.nextLine();
//        dada la long del prefijo de red devolver con el formato bin punteado la máscara de red
        StringBuilder mascaraSubred = this.mascaraBin(longitudPrefijoRed);

        String[] octetosMascara = mascaraSubred.toString().split("\\.");

//        dada la la máscara de red con el formato bin punteado devolverla con el formato decimal punteado
        StringBuilder octetosMascaraADecimal = this.octetoMascaraADecimal(octetosMascara);
        System.out.print("- mascara de subred bin: ");
        System.out.println(mascaraSubred);

        System.out.print("- mascara de subred dec: ");
        System.out.println(octetosMascaraADecimal);

        StringBuilder devolverPrimeraDireccion = this.devolverPrimeraDireccion(octetosMascaraADecimal, ipv4);
        System.out.print("- Primera Direccion: ");
        System.out.println(devolverPrimeraDireccion);

        this.numeroDeHosts(mascaraSubred);

        return false;
    }

    private void ipv4ABin(StringBuilder ipv4) {
        String[] octetos = ipv4.toString().split("\\.");
        //rellenar 0 a izda

        int dividendo = 0;
        int cociente = 0;
        StringBuilder octetoADecimal = new StringBuilder();

        for (String octeto : octetos) {
            StringBuilder octDecimal = new StringBuilder();
            if (octeto.length() < 8) {
                int longitud = 8 - octeto.length();
                for (int i = 0; i < longitud; i++) {
                    octDecimal.append(0);
                }
                octDecimal.append(octeto);
            }
            StringBuilder octetor = new StringBuilder();
            dividendo = Integer.parseInt(octDecimal.toString());
            if (dividendo == 1) {
                octetor.append(1);

            } else if (dividendo == 0) {
                octetor.append(0);
            } else {
                while (dividendo > 1) {
                    octetor.append(dividendo % 2);
                    dividendo /= 2;
                    cociente = dividendo;
                }
                octetor.reverse();
                octetor.insert(0, cociente);
            }

            if (octetor.length() < 8) {
                int longitud = 8 - octetor.length();
                for (int i = 0; i < longitud; i++) {
                    octetor.insert(0, 0);
                }
            }

            octetoADecimal.append(octetor);
            octetoADecimal.append(".");
        }

        octetoADecimal.deleteCharAt(octetoADecimal.length() - 1);
        System.out.println("ipv4 a BIN: " + octetoADecimal);
    }

    private StringBuilder mascaraBin(String longitudPrefijoRed) {
        final int LONGITUD_MASCARA = 32;
        StringBuilder mascaraSubred = new StringBuilder(35);
        for (int i = 1; i <= LONGITUD_MASCARA; i++) {
            if (i <= Integer.parseInt(longitudPrefijoRed)) {
                mascaraSubred.append("1");
                if (i % 8 == 0 && i < LONGITUD_MASCARA) {
                    mascaraSubred.append(".");
                }
            } else {
                mascaraSubred.append(0);
                if (i % 8 == 0 && i < LONGITUD_MASCARA) {
                    mascaraSubred.append(".");
                }
            }
        }
        return mascaraSubred;
    }

    private StringBuilder octetoMascaraADecimal(String[] octetosMascara) {
        StringBuilder mascaraDecimal = new StringBuilder();
        int multip;
        int c;
        for (String octetoMascaraToInt : octetosMascara) {
            int octeto = 0;
            for (int position = octetoMascaraToInt.length() - 1; position >= 0; position--) {
                multip = (int) Math.pow(2, position);
                c = Character.getNumericValue(octetoMascaraToInt.charAt(position));
                octeto += (c * multip);
            }
            mascaraDecimal.append(octeto);
            mascaraDecimal.append(".");
        }

        mascaraDecimal.deleteCharAt(mascaraDecimal.length() - 1);
        return mascaraDecimal;
    }

    private StringBuilder devolverPrimeraDireccion(StringBuilder mascaraDec, StringBuilder ipv4Dec) {
        StringBuilder st = new StringBuilder();
        String[] a = mascaraDec.toString().split("\\.");
        String[] ab = ipv4Dec.toString().split("\\.");

        for (int i = 0; i < a.length; i++) {
            int mask = Integer.parseInt(a[i]);
            int ipv4 = Integer.parseInt(ab[i]);
            int ip = mask & ipv4;
            st.append(ip);
            st.append(".");
        }
        st.deleteCharAt(st.length() - 1);
        return st;
    }

    private void devolverDireccionBroadcast() {

    }

    private void devolverDireccionesHost() {

    }

    private int numeroDeHosts(StringBuilder mascaraSubred) {
        int longitud = 32;
        int numeroHosts = 0;
        int index = mascaraSubred.indexOf("0");
        System.out.println("- index: " + index);

        if (index >= 9 && index < 17) {
            index -= 1;
        }
        if (index >= 18 && index < 26) {
            index -= 2;
        }
        if (index >= 27) {
            index -= 3;
        }

        System.out.println("- index: " + index);
        System.out.println("mascaraSubred" + mascaraSubred);
        int bitsACeroDeMascara = longitud - index;
        System.out.println("- bitsACeroDeMascara: " + bitsACeroDeMascara);
        if (bitsACeroDeMascara <= 2) {
            numeroHosts = 0;
        } else {
            numeroHosts = (int) (Math.pow(2, bitsACeroDeMascara) - 2);
        }
        System.out.println("- numero de Hosts: " + numeroHosts);
        return numeroHosts;
    }
    
    
}
