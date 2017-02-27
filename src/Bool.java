/**
 * Created by roscale on 2/27/17.
 */
public class Bool implements NetworkPacket {

    boolean value;

    Bool(boolean value)
    {
        this.value = value;
    }

    @Override
    public String encode()
    {
        return (value ? "1" : "0");
    }

    static Bool decode(String rawPacket)
    {
        return new Bool( Boolean.parseBoolean(!"0".equals(rawPacket) ? "true" : "false") );
    }
}
