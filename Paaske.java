public class Paaske {
    
    public static int tæller;

    public Paaske(){
        int tæller = 0;
    }

    public static int pusPaaske(){
        return tæller++;
    }

    public static int getTæller() {
        return tæller;
    }

    public static void printPaaske(){
        System.out.println("Du har samlet " + getTæller() + " påskeæg");
    }


}
