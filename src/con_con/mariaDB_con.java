package con_con;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class mariaDB_con {
	//mariaDB 와 연결 할 수 있게 함.
    private static final String DB_driver_class = "org.mariadb.jdbc.Driver";
    private static final String DB_url = "jdbc:mariadb://localhost:3307/";
    private static final String DB_username = "root";
    private static final String DB_password = "doje";
    
    //import해서 가져옴.
    Connection con;
    PreparedStatement psmt;
    ResultSet rs;
    
    //연결 잘 되는지 확인.
    private void connectDB() {
        try {
            Class.forName(DB_driver_class);
            con = DriverManager.getConnection(DB_url, DB_username, DB_password);
            System.out.println("mariadb와 연결 됨");

            con.setCatalog("dnwns");

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("DB 연결 실패");
        }
    }
    
    //mariaDB member 테이블에 있는 모든 정보 가져옴.
    private void AIIuserinfo() {
        try {
            String sql = "SELECT * FROM member";
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                String user_num = rs.getString("user_num");
                String user_name = rs.getString("user_name");
                String decision = rs.getString("decision");
                System.out.print("user_num : " + user_num);
                System.out.print(", user_name : " + user_name);
                System.out.println(", decision : " + decision);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //mariaDB member 테이블에 있는 user_Num을 사용해서
    //콘솔창에 입력하는 숫자로 그 입력한 숫자에 맞게 user_num에서 정보 가져옴.
    private void searchByUserNum(int user_Num) {
        try {
            String sql = "SELECT * FROM member WHERE user_num = ?";

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, user_Num);

            rs = psmt.executeQuery();
            while (rs.next()) {
                String user_num = rs.getString("user_num");
                String user_name = rs.getString("user_name");
                String decision = rs.getString("decision");
                System.out.print("user_num : " + user_num);
                System.out.print(", user_name : " + user_name);
                System.out.println(", decision : " + decision);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //mariaDB member 테이블에 있는 user_name을 사용해서
    //콘솔창에 이름을 입력하면 그 입력한 이름에 맞게 user_name에서 정보 가져옴.
    private void Person_Name(String User_Name_Num) {
		try {
			String sql = "SELECT * FROM member WHERE user_name = ?";
			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, User_Name_Num);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				String user_num = rs.getString("user_num");
				String user_name = rs.getString("user_name");
				String decision = rs.getString("decision");
				System.out.print("user_num : " + user_num);
                System.out.print(", user_name : " + user_name);
                System.out.println(", decision : " + decision);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
	}
    }
    
    private void recovery_delete(String decision, String user_name) {
    	try {
    		String sql = "UPDATE member SET decision = ? WHERE user_name = ?";
    		
    		psmt = con.prepareStatement(sql);
    		psmt.setString(1, decision);
    		psmt.setString(2, user_name);
    		
    		int UPUPUP = psmt.executeUpdate();
    		
    		if(UPUPUP == 1) {
    			System.out.println("업데이트에 성공했다 맨유 보러가자" + ", " + UPUPUP);
    		}
    		else {
    			System.out.println("업데이트가 되지 않았다 친구야");
    		}
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void Person_Add(String USER_NAME, String Decision) {
    	try {
    		String sql = "INSERT INTO member (user_name, decision, reg_date) values (?, ?, CURRENT_TIMESTAMP)";
    		psmt = con.prepareStatement(sql);
    		psmt.setString(1, USER_NAME);
    		psmt.setString(2, Decision);
    		
    		int ADD = psmt.executeUpdate();
    		if(ADD == 1) {
    			System.out.println("추가가 잘 되었다 맨시티 보러가자" + ", " + ADD);
    		}
    		else {
    			System.out.println("추가가 되지 않았어!");
    		}
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
    	//연결 잘 되는지 확인.
        mariaDB_con mariadb = new mariaDB_con();
        mariadb.connectDB();
        
        //연결된 mariaDB에서 member 테이블에 모든 정보 가져옴.
        mariadb.AIIuserinfo();
        //스캐너 사용.
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n위에 있는건 DB 정보들이야 그냥 그렇다고");
        //while문에 true를 주어서 반복되게 만듬 물론 break를 사용하면 빠져나감.
        while(true) {
        System.out.println("\n메뉴을 선택해줄래? 1.유저번호 검색 2.유저이름 검색 3.유저 Y/N(복구/삭제) 4.사람 추가 5.종료");
        char ch = scanner.next().charAt(0);
        
        //유니코드를 이용하여 콘솔창에 1를 입력하면 실행됨.
        if (ch == 49) {
            System.out.println("\n[1]몇번째 번호를 가져오고 싶어?");
            int userNum = scanner.nextInt();
            // 콘솔창에 입력한 번호를 가져옴
            mariadb.searchByUserNum(userNum);
            System.out.println("\n" + userNum + "를 가져오고 싶어구나");
        }
        //유니코드를 이용하여 콘솔창에 2를 입력하면 실행됨.
        else if(ch == 50) {
        	System.out.println("\n[2]몇번째 이름을 가져올래?");
        	String user_Name = scanner.next();
        	//콘솔창에 입력한 이름을 가져옴.
        	mariadb.Person_Name(user_Name);
        	System.out.println("\n" + user_Name + "를 가져오고 싶었구나");
        }
        //유니코드를 이용하여 콘솔창에 3를 입력하면 실행됨.
        else if(ch == 51) {
        System.out.println("\n[3] 복구(Y) 또는 삭제(N) 하실껀가요?");
        System.out.println("\n삭제하거나 복구 하실 Y/N 을 입력해주세요");
        String decision = scanner.next();
        System.out.println("누구를 삭제하거나 복구 하실지 누군가에 이름을 써주세요");
        String USER_NAME = scanner.next();
        mariadb.recovery_delete(decision, USER_NAME);
        }
        //유니코드를 이용하여 콘솔창에 4를 입력하면 실행됨.
        else if(ch == 52) {
        	System.out.println("\n[4]사람을 추가하실껀가요?");
        	System.out.println("추가할 사람에 이름을 적어주세요");
        	String UN = scanner.next();
        	System.out.println("추가 할 사람에 decision를 적어주세요");
        	String Decision = scanner.next();
        	mariadb.Person_Add(UN, Decision);
        }
        //유니코드를 이용하여 콘솔창에 5를 입력하면 실행되고 while문을 빠져나감.
        else if(ch == 53) {
        	System.out.println("즐거웠어요 나중에 또 오세요");
        	break;
        }
        else {
        	System.out.println("뿌뿌 잘못입력하셨습니다 다시 입력하세요");
        }
            
        }
    }
}
