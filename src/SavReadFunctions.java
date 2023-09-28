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
