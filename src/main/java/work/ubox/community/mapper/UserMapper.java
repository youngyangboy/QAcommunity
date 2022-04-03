package work.ubox.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import work.ubox.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED) values ( #{name}, #{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from USER where TOKEN=#{token}")
    User findByToken(@Param("token")String token);
}
