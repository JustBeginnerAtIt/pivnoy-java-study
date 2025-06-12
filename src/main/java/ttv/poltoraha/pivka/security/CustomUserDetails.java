package ttv.poltoraha.pivka.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ttv.poltoraha.pivka.entity.MyUser;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final MyUser user;

    public CustomUserDetails(MyUser user) {
        this.user = user;
    }

    public Boolean needsPasswordReset () {
        return user.isNeedsPasswordReset();
    }

    @Override
    public Collection<? extends GrantedAuthority>  getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
