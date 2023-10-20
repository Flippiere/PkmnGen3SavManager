import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;



public class SavReadFunctions {
    static final HashMap<Long, Integer> growthOffsetHashMap;
    static {
    growthOffsetHashMap = new LinkedHashMap<>();
    growthOffsetHashMap.put((long)0,0);
    growthOffsetHashMap.put((long)1,0);
    growthOffsetHashMap.put((long)2,0);
    growthOffsetHashMap.put((long)3,0);
    growthOffsetHashMap.put((long)4,0);
    growthOffsetHashMap.put((long)5,0);
    growthOffsetHashMap.put((long)6,12);
    growthOffsetHashMap.put((long)7,12);
    growthOffsetHashMap.put((long)8,24);
    growthOffsetHashMap.put((long)9,36);
    growthOffsetHashMap.put((long)10,24);
    growthOffsetHashMap.put((long)11,36);
    growthOffsetHashMap.put((long)12,12);
    growthOffsetHashMap.put((long)13,12);
    growthOffsetHashMap.put((long)14,24);
    growthOffsetHashMap.put((long)15,36);
    growthOffsetHashMap.put((long)16,24);
    growthOffsetHashMap.put((long)17,36);
    growthOffsetHashMap.put((long)18,12);
    growthOffsetHashMap.put((long)19,12);
    growthOffsetHashMap.put((long)20,24);
    growthOffsetHashMap.put((long)21,36);
    growthOffsetHashMap.put((long)22,24);
    growthOffsetHashMap.put((long)23,36);}

    static final HashMap<Long, Integer> attackOffsetHashMap;
    static {
    attackOffsetHashMap = new LinkedHashMap<>();
    attackOffsetHashMap.put((long)0,12);
    attackOffsetHashMap.put((long)1,12);
    attackOffsetHashMap.put((long)2,24);
    attackOffsetHashMap.put((long)3,36);
    attackOffsetHashMap.put((long)4,24);
    attackOffsetHashMap.put((long)5,36);
    attackOffsetHashMap.put((long)6,0);
    attackOffsetHashMap.put((long)7,0);
    attackOffsetHashMap.put((long)8,0);
    attackOffsetHashMap.put((long)9,0);
    attackOffsetHashMap.put((long)10,0);
    attackOffsetHashMap.put((long)11,0);
    attackOffsetHashMap.put((long)12,24);
    attackOffsetHashMap.put((long)13,36);
    attackOffsetHashMap.put((long)14,12);
    attackOffsetHashMap.put((long)15,12);
    attackOffsetHashMap.put((long)16,36);
    attackOffsetHashMap.put((long)17,24);
    attackOffsetHashMap.put((long)18,24);
    attackOffsetHashMap.put((long)19,36);
    attackOffsetHashMap.put((long)20,12);
    attackOffsetHashMap.put((long)21,12);
    attackOffsetHashMap.put((long)22,36);
    attackOffsetHashMap.put((long)23,24);}

    static final HashMap<Long, Integer> evCondOffsetHashMap;
    static {
    evCondOffsetHashMap = new LinkedHashMap<>();
    evCondOffsetHashMap.put((long)0,24);
    evCondOffsetHashMap.put((long)1,36);
    evCondOffsetHashMap.put((long)2,12);
    evCondOffsetHashMap.put((long)3,12);
    evCondOffsetHashMap.put((long)4,36);
    evCondOffsetHashMap.put((long)5,24);
    evCondOffsetHashMap.put((long)6,24);
    evCondOffsetHashMap.put((long)7,36);
    evCondOffsetHashMap.put((long)8,12);
    evCondOffsetHashMap.put((long)9,12);
    evCondOffsetHashMap.put((long)10,36);
    evCondOffsetHashMap.put((long)11,24);
    evCondOffsetHashMap.put((long)12,0);
    evCondOffsetHashMap.put((long)13,0);
    evCondOffsetHashMap.put((long)14,0);
    evCondOffsetHashMap.put((long)15,0);
    evCondOffsetHashMap.put((long)16,0);
    evCondOffsetHashMap.put((long)17,0);
    evCondOffsetHashMap.put((long)18,36);
    evCondOffsetHashMap.put((long)19,24);
    evCondOffsetHashMap.put((long)20,36);
    evCondOffsetHashMap.put((long)21,24);
    evCondOffsetHashMap.put((long)22,12);
    evCondOffsetHashMap.put((long)23,12);}

    static final HashMap<Long, Integer> miscOffsetHashMap;
    static {
    miscOffsetHashMap = new LinkedHashMap<>();
    miscOffsetHashMap.put((long)0,36);
    miscOffsetHashMap.put((long)1,24);
    miscOffsetHashMap.put((long)2,36);
    miscOffsetHashMap.put((long)3,24);
    miscOffsetHashMap.put((long)4,12);
    miscOffsetHashMap.put((long)5,12);
    miscOffsetHashMap.put((long)6,36);
    miscOffsetHashMap.put((long)7,24);
    miscOffsetHashMap.put((long)8,36);
    miscOffsetHashMap.put((long)9,24);
    miscOffsetHashMap.put((long)10,12);
    miscOffsetHashMap.put((long)11,12);
    miscOffsetHashMap.put((long)12,36);
    miscOffsetHashMap.put((long)13,24);
    miscOffsetHashMap.put((long)14,36);
    miscOffsetHashMap.put((long)15,24);
    miscOffsetHashMap.put((long)16,12);
    miscOffsetHashMap.put((long)17,12);
    miscOffsetHashMap.put((long)18,0);
    miscOffsetHashMap.put((long)19,0);
    miscOffsetHashMap.put((long)20,0);
    miscOffsetHashMap.put((long)21,0);
    miscOffsetHashMap.put((long)22,0);
    miscOffsetHashMap.put((long)23,0);}
    
    //0 indexed 
    public static int readID(byte[] pkmn){
        byte[] personalityValue = {pkmn[0],pkmn[1],pkmn[2],pkmn[3]};
        byte[] trainerId = {pkmn[4],pkmn[5],pkmn[6],pkmn[7]};
        byte[] decryptionKey = {0,0,0,0};
        for(int i2 = 0; i2 < 4; i2++){
            decryptionKey[i2] = (byte) ((int)personalityValue[i2] ^ (int)trainerId[i2]);
        }
        long personalityValueAsInt = bytesToInt(personalityValue);
        long ordering = personalityValueAsInt % 24;
        int growthOffset = growthOffsetHashMap.get(ordering);

        byte[] pokemonId = {(byte)((int)pkmn[growthOffset+32] ^ (int) decryptionKey[0]),(byte)((int)pkmn[growthOffset+33] ^ (int) decryptionKey[1])};

        return (int) bytesToInt(pokemonId);
    }

    // function that takes a slot no then returns the 80 bytes in that slot
    public static byte[] readPKMN(int slot, byte[] saveFile) throws Exception{
        //pointed address needs to find the correct first address,
        //because gen 3 saves sections get rotated, This is done through the Section id
        //found unecrypted at 0xFF4 of a section.
        int firstAddress = firstBoxByte(saveFile);
        int pointedAddress =(firstAddress + (slot*80) + (int) (Math.floor(((slot*80)+4)/3968))*128)%57344;
        if(!isSaveA(saveFile)){pointedAddress = pointedAddress + 57344;}
        System.out.println(pointedAddress);
        byte[] pkmn = new byte[80];
        for(int i=0;i<80;i++){
            pkmn[i] = saveFile[pointedAddress];
            pointedAddress = pointedAddress + 1;
            if(pointedAddress - (Math.floor(pointedAddress/4096)*4096) >= (3968)){
                if(pointedAddress % 57280 == 0){
                    pointedAddress = pointedAddress - 57216;
                    System.out.println(pointedAddress);
                }
                else{pointedAddress = pointedAddress + 128;}
            }
        }
        return pkmn;
    }

    public static String indexNameLookup(int indexNo) throws FileNotFoundException, IOException{
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings = Files.readAllLines(Paths.get("index.txt"));

        return (listOfStrings.get(indexNo));
    }

    public static int hpLookup(byte[] pkmn){
        return 0;
    }

    public static long bytesToInt(byte[] bytes){
        int i = 0;
        long byteAsInt = 0;

        while (i < bytes.length){
            byteAsInt = byteAsInt * 256;
            if((bytes[bytes.length - (i+1)]) < 0){
                byteAsInt = byteAsInt + ((bytes[bytes.length - (i+1)]) + 256);
            }
            else{
                byteAsInt = byteAsInt + bytes[bytes.length - (i+1)];
            }
            i = i+1;
        }
        return byteAsInt;
    }

    public static boolean isSaveA(byte[] saveFile){
        if(saveFile[57340]>saveFile[114684]){return true;}
        else{return false;}
    }

    public static int firstBoxByte(byte[] saveFile) throws Exception{
        int bytePointer;
        boolean found;
        found = false;
        if(isSaveA(saveFile)){bytePointer = 4084;}
        else{bytePointer = 61428;}
        while(!found){
            if(saveFile[bytePointer] == 5){
                bytePointer = bytePointer - 4080;
                return bytePointer;
            }
            else{
                bytePointer = bytePointer + 4096;
            }
            if(bytePointer > 114684){
                throw new Exception("index not found");
            }
        }
        return bytePointer;
    }
}
