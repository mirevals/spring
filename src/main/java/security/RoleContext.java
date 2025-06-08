package security;

public class RoleContext {
    private static final ThreadLocal<Role> roleHolder = new ThreadLocal<>();

    public static void setRole(Role role) {
        roleHolder.set(role);
    }

    public static Role getRole() {
        return roleHolder.get();
    }

    public static void clear() {
        roleHolder.remove();
    }
}
