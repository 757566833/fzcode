//package com.fzcode.authservice.service;
//
//import com.fzcode.authservice.bean.MyUserDetails;
//import com.fzcode.authservice.dto.UserDetailsDTO;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class UserServicebak extends JdbcUserDetailsManager {
//    public static final String USERS_BY_USERNAME_QUERY = "select username,password,enabled,expired,locked,register_type "
//            + "from users " + "where username = ?";
//
//    public static final String CREATE_USER_SQL = "insert into users (username, password, enabled, expired, locked, register_type) values (?,?,?,?,?,?)";
//    public static final String INSERT_AUTHORITY_SQL = "insert into authorities (username, authority) values (?,?)";
//    public static final String CHANGE_PASSWORD_SQL = "update users set password = ? where username = ?";
//
//    public UserServicebak() {
//    }
//
//    public UserServicebak(DataSource dataSource) {
//        setDataSource(dataSource);
//    }
//
//
//    @Override
//    public UserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
//        List<UserDetailsDTO> users = loadMyUsersByUsername(username);
//        if (users.size() == 0) {
//            this.logger.debug("Query returned no results for user '" + username + "'");
//
//            throw new UsernameNotFoundException(
//                    this.messages.getMessage("JdbcDaoImpl.notFound",
//                            new Object[]{username}, "Username {0} not found"));
//        }
//
//        UserDetailsDTO user = users.get(0); // contains no GrantedAuthority[]
//
//        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
//
//        if (super.getEnableAuthorities()) {
//            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
//        }
//
//        if (super.getEnableGroups()) {
//            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
//        }
//
//        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
//
//        addCustomAuthorities(user.getUsername(), dbAuths);
//
//        if (dbAuths.size() == 0) {
//            this.logger.debug("User '" + username
//                    + "' has no authorities and will be treated as 'not found'");
//
//            throw new UsernameNotFoundException(this.messages.getMessage(
//                    "JdbcDaoImpl.noAuthority", new Object[]{username},
//                    "User {0} has no GrantedAuthority"));
//        }
//
//        return createMyUserDetails(username, user, dbAuths);
//    }
//
//    public void createUser(final MyUserDetails user) {
//        getJdbcTemplate().update(CREATE_USER_SQL, ps -> {
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            ps.setBoolean(3, user.isEnabled());
//            ps.setBoolean(4, user.isAccountNonExpired());
//            ps.setBoolean(5, user.isAccountNonLocked());
//            ps.setString(6, user.getRegisterType());
//        });
//        insertUserAuthorities(user);
//    }
//
//    private void insertUserAuthorities(MyUserDetails user) {
//        for (GrantedAuthority auth : user.getAuthorities()) {
//            getJdbcTemplate().update(INSERT_AUTHORITY_SQL, user.getUsername(),
//                    auth.getAuthority());
//        }
//    }
//
//
//    public int resetPassword(String username, String password) {
//        return getJdbcTemplate().update(CHANGE_PASSWORD_SQL, password, username);
//    }
//
//    public int changePassword(String username,String oldPassword,String newPassword){
//
//        return getJdbcTemplate().update(CHANGE_PASSWORD_SQL, newPassword, username);
//    }
//    public List<UserDetailsDTO> loadMyUsersByUsername(String username) {
//        return getJdbcTemplate().query(this.USERS_BY_USERNAME_QUERY,
//                new String[]{username}, (rs, rowNum) -> {
//                    String username1 = rs.getString(1);
//                    String password = rs.getString(2);
//                    boolean enabled = rs.getBoolean(3);
//                    boolean expired = rs.getBoolean(4);
//                    boolean locked = rs.getBoolean(5);
//                    String registerType = rs.getString(6);
//                    return new UserDetailsDTO(
//                            username1,
//                            password,
//                            enabled,
//                            expired,
//                            locked,
//                            AuthorityUtils.NO_AUTHORITIES,
//                            registerType
//                    );
//                });
//    }
//
//    protected UserDetailsDTO createMyUserDetails(String username, UserDetailsDTO userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
//        String returnUsername = userFromUserQuery.getUsername();
//
//        if (!super.isUsernameBasedPrimaryKey()) {
//            returnUsername = username;
//        }
//
//        return new UserDetailsDTO(
//                returnUsername,
//                userFromUserQuery.getPassword(),
//                userFromUserQuery.isAccountNonExpired(),
//                userFromUserQuery.isAccountNonLocked(),
//                userFromUserQuery.isEnabled(),
//                combinedAuthorities,
//                userFromUserQuery.getRegisterType()
//        );
//    }
//
//}
