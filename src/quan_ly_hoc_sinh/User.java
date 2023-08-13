package quan_ly_hoc_sinh;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters (nếu cần)

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
    }

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRole() {
		// TODO Auto-generated method stub
		return null;
	}
}

