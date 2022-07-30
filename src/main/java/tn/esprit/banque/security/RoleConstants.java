package tn.esprit.banque.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleConstants {
	public static GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
	public static GrantedAuthority ROLE_AGENT = new SimpleGrantedAuthority("ROLE_AGENT");
	public static GrantedAuthority ROLE_PHYSIQUE = new SimpleGrantedAuthority("ROLE_PHYSIQUE");
	public static GrantedAuthority ROLE_MORALE = new SimpleGrantedAuthority("ROLE_MORALE");

}
