import java.io.Serializable;

public class PDU implements Serializable {

    private int ID;
    private static int IDCount = 1;
    private int size;
    private String msg;
    private String checksum;

    public PDU(){
        this.ID =IDCount++;
        this.size = 17;
        this.msg = this.checksum = "n/a";
    }

    public void setMsg(String message) {
        this.msg=message;
        this.size+= 2*message.length();
    }

    public void setChecksum(String checksum) {
        this.checksum=checksum;
        this.size += 2*checksum.length();
    }

    @Override
    public String toString() {
        return   "ID=" + ID +
                ", size=" + size +
                ", msg='" + msg + '\'' +
                ", checksum='" + checksum ;
    }
}
