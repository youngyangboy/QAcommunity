package work.ubox.community.mapper;

import org.apache.ibatis.annotations.*;
import work.ubox.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED,AVATAR_URL) values ( #{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from USER where TOKEN=#{token}")
    User findByToken(@Param("token")String token);

    @Select("select * from USER where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from USER where account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name=#{name},token=#{token},avatar_url=#{avatarUrl},gmt_modified=#{gmtModified} where id = #{id}")
    void update(User user);
}
