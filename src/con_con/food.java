
package con_con;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class food {

    private ManageDAO dao;  // ManageDAO 인스턴스 변수 추가
    private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://localhost:3307/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "doje";
    private Connection con;
    private PreparedStatement psmt;
    private ResultSet rs1;
    private Scanner sc = new Scanner(System.in);

    public food() {
        dao = new ManageDAO();  // ManageDAO 인스턴스 생성
        connectDB();
    }

    private Connection connectDB() {
        Connection con = null;
        try {
            Class.forName(DB_DRIVER_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            con.setCatalog("dnwns");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void insertFood() {
        ManageDTO dto = getUserInput();
        insertfooddb(dto);
    }

    private void insertfooddb(ManageDTO dto) {
        if (dto.getFood_name().length() <= 10) {
            String query = "INSERT INTO food (food_name) VALUES (?)";
            try {
                psmt = connectDB().prepareStatement(query);
                psmt.setString(1, dto.getFood_name());
                psmt.executeUpdate();
                System.out.println("데이터가 성공적으로 추가되었습니다.");
            } catch (SQLException e) {
                System.out.println("중복된 값이 있거나 오류가 발생했습니다.");
            }
        } else {
            System.out.println("글자 길이를 10글자 이내로 해주십시오");
        }
    }

    private ManageDTO getUserInput() {
        ManageDTO dto = new ManageDTO();
        System.out.print("음식 이름을 입력하세요: ");
        dto.setFood_name(sc.next());
        return dto;
    }

    public void DeleteFood() {
        ManageDTO dto = new ManageDTO();
        DeleteFoodDB(dto);
    }

    private void DeleteFoodDB(ManageDTO dto) {
        String sql = "SELECT * FROM food";

        try {
            psmt = connectDB().prepareStatement(sql);
            rs1 = psmt.executeQuery();

            System.out.println("번호 | 음식");
            while (rs1.next()) {
                int num = rs1.getInt(1);
                String food = rs1.getString(2);

                System.out.println(num + "|" + food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("삭제하고 싶은 음식의 번호를 누르세요");
        int select = sc.nextInt();

        sql = "DELETE FROM food WHERE food_num = " + select + ";";

        try {
            psmt = connectDB().prepareStatement(sql);
            rs1 = psmt.executeQuery();
            System.out.println(select + "번째 행이 삭제되었습니다");
        } catch (Exception e) {
            System.out.println("삭제중 오류가 발생하였습니다.");
        }
    }

    public void Modfood() {
        ManageDTO dto = new ManageDTO();
        Modifyfood(dto);
    }

    private void Modifyfood(ManageDTO dto) {
        String sql = "SELECT * FROM food";

        try {
            psmt = connectDB().prepareStatement(sql);
            rs1 = psmt.executeQuery();

            System.out.println("번호 | 음식");
            while (rs1.next()) {
                int num = rs1.getInt(1);
                String food = rs1.getString(2);

                System.out.println(num + "|" + food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("수정하고 싶은 음식의 번호를 입력하세요");
        int select = sc.nextInt();
        System.out.println("수정할 값을 입력하세요");
        String name = sc.next();

        sql = "UPDATE food SET food_name = ? WHERE food_num = ?";

        try {
            psmt = connectDB().prepareStatement(sql);
            psmt.setString(1, name);
            psmt.setInt(2, select);

            int rowsAffected = psmt.executeUpdate();
            System.out.println(rowsAffected + "개의 행이 수정되었습니다");
        } catch (SQLException e) {
            System.out.println("수정 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }
    }
}
