package authservice.serializer;

import authservice.model.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String arg0, UserInfoDto arg1) {
        byte[] retval= null;
        ObjectMapper objectMapper= new ObjectMapper();
        try{
            retval= objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception ex){
            ex.getStackTrace();
        }
        return retval;
    }

    @Override
    public void close() {
    }
}
