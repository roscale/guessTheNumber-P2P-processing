/**
 * Created by roscale on 2/27/17.
 */
public class Number implements NetworkPacket {

    int value;

    Number(int value)
    {
        this.value = value;
    }

    @Override
    public String encode()
    {
        return String.valueOf(value);
    }

    static Number decode(String rawPacket)
    {
        return new Number( Integer.parseInt(rawPacket) );
    }
}
