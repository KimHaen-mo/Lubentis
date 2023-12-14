package con_con;

import java.lang.reflect.Member;
//import해서 쓸거 가져옴.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
	
	public class ManageDAO {
		//mariadb와 연결 해줌.
	    private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
	    private static final String DB_URL = "jdbc:mariadb://localhost:3307/";
	    private static final String DB_USERNAME = "root";
	    private static final String DB_PASSWORD = "doje";
	    
	    //쿼리문때 쓸꺼 임.
	    private Connection con;
	    private PreparedStatement psmt;
	    private ResultSet rs;
	    
	    //연결 잘 되는지 확인 connectDB(); 메서드호출
	    public ManageDAO() {
	        connectDB();
	    }

	    private void connectDB() {
	        try {
	            Class.forName(DB_DRIVER_CLASS);
	            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	            System.out.println("mariaDB와 연결이 잘 됬어 맨유 보러가장!");
	            con.setCatalog("dnwns");
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //DB에 있는 모든 정보들 가져옴 "SELECT * FROM member";
	    public List<ManageDTO> AllUserInfo() {
	        List<ManageDTO> userList = new ArrayList<>();
	        try {
	            String sql = "SELECT * FROM member";
	            psmt = con.prepareStatement(sql);
	            rs = psmt.executeQuery();

	            while (rs.next()) {
	            	ManageDTO member = new ManageDTO();
	                member.setUser_num(rs.getString("user_num"));
	                member.setUser_name(rs.getString("user_name"));
	                member.setDecision(rs.getString("decision"));
	                userList.add(member);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return userList;
	    }
	    
	    public List<ManageDTO> AllfoodInfo() {
	        List<ManageDTO> userList = new ArrayList<>();
	        try {
	            String sql = "SELECT * FROM food";
	            psmt = con.prepareStatement(sql);
	            rs = psmt.executeQuery();

	            while (rs.next()) {
	            	ManageDTO food = new ManageDTO();
	                food.setFood_num(rs.getString("food_num"));
	                food.setFood_name(rs.getString("food_name"));
	                userList.add(food);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return userList;
	    }
	    
	    public List<ManageDTO> searchMember(String searchName) {
			// return
			List<ManageDTO> dds = new Vector<ManageDTO>();
			//
			String sql = "SELECT m.user_name, f.food_name\r\n"
					+ "				FROM terminal t\r\n"
					+ "				JOIN member m ON m.user_num = t.member_num \r\n"
					+ "				JOIN food f ON f.food_num = t.food_num\r\n"
					+ "				WHERE m.user_name = \"" + searchName + "\" \r\n"
					+ "				AND m.decision = \"Y\";";
			try {
				psmt = con.prepareStatement(sql);
				rs = psmt.executeQuery(sql);
				while (rs.next()) {
					ManageDTO dto = new ManageDTO();
					dto.setUser_name(rs.getString("user_name"));
					dto.setFood_name(rs.getString("food_name"));
					dds.add(dto);
				}
			} catch (Exception e) {
				System.out.println("검색 과정 중 오류 발생하였습니다." + psmt);
				e.printStackTrace();
			}
			return dds;
		}
		
		public List<ManageDTO> searchFood(String searchFood) {
			// return
			List<ManageDTO> dds = new Vector<ManageDTO>();
			//음식 조회 하는 쿼리문
			String sql = "SELECT m.user_name, f.food_name\r\n"
					+ "		FROM terminal t\r\n"
					+ "		JOIN member m ON m.user_num = t.member_num \r\n"
					+ "		JOIN food f ON f.food_num = t.food_num\r\n"
					+ "		WHERE f.food_name = \"" + searchFood + "\"";

			try {
				psmt = con.prepareStatement(sql);
				rs = psmt.executeQuery(sql);
				while (rs.next()) {
					ManageDTO dto = new ManageDTO();
					dto.setFood_name(rs.getString("food_name"));
					dto.setUser_name(rs.getString("user_name"));
					dds.add(dto);
				}
			} catch (Exception e) {
				System.out.println("검색 과정 중 오류 발생하였습니다." + psmt);
				e.printStackTrace();
			}
			return dds;
		}

		public int addPerson(String memberName) {
			int result = 0;
			try {
				String sql = "INSERT INTO member (user_name) VALUES (?)";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, memberName);

				result = psmt.executeUpdate();
				if (result == 1) {
					System.out.println("추가가 잘 되었다 맨시티 보러가자" + ", " + result);
				} else {
					System.out.println("추가가 되지 않았어!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		public int recovery_Delete(String decision, String memberName) {
			int result = 0;
			try {
				String sql = "UPDATE member SET decision = ? WHERE user_name = ?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, decision);
				psmt.setString(2, memberName);

				result = psmt.executeUpdate();
				if (result == 1) {
					System.out.println("업데이트에 성공했다 맨유 보러가자" + ", " + result);
				} else {
					System.out.println("업데이트가 되지 않았다 친구야");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		/*
		 * public int user_addfood(String user, String addfood) { int result = 0;
		 * ResultSet memberRs = null; int terminalResult = 0; // 추가 : 터미널에 음식을 추가한 결과를
		 * 저장할 변수
		 * 
		 * try { // 먼저 음식이 있는지 food 테이블에서 확인함. String foodsql =
		 * "SELECT * FROM food WHERE food_name = ?"; // psmt에 쿼리문 넣음. psmt =
		 * con.prepareStatement(foodsql); psmt.setString(1, addfood); // 쿼리문 실행하고 rs 담음.
		 * rs = psmt.executeQuery(); // 이미 존재하는 음식에 번호를 가져와서 terminal 테이블에 추가함. if
		 * (rs.next()) { String terminalsql =
		 * "INSERT INTO terminal (member_num, food_num) VALUES (?, ?)"; psmt =
		 * con.prepareStatement(terminalsql); String membersql =
		 * "SELECT user_num FROM member WHERE user_name = ?"; PreparedStatement
		 * memberPsmt = con.prepareStatement(membersql);
		 * 
		 * memberPsmt.setString(1, user); memberRs = memberPsmt.executeQuery();
		 * 
		 * if (memberRs != null && memberRs.next()) { psmt.setString(1,
		 * memberRs.getString("user_num")); // 이미 존재하는 음식에 번호를 가져와서 terminal 테이블에 추가
		 * psmt.setString(2, rs.getString("food_num")); // terminal에 추가한 결과를 따로 저장
		 * terminalResult = psmt.executeUpdate(); } // UPDATE 되면 terminalResult가 0에서 1이
		 * 되고 if문 성공. if (terminalResult == 1) { System.out.println("음식 추가됨 아스날 보러가자" +
		 * ", " + terminalResult); } else { // 그렇지 않으면 실패.
		 * System.out.println("음식 추가에 실패했어...."); } } else { // 음식이 food 테이블에 없는 경우
		 * String FoodSql = "INSERT INTO food (food_name) VALUES (?)"; psmt =
		 * con.prepareStatement(FoodSql); psmt.setString(1, addfood);
		 * 
		 * int foodResult = psmt.executeUpdate();
		 * 
		 * if (foodResult == 1) { // 새로 추가된 음식의 번호를 가져와서 terminal에 추가 String foodnumsql
		 * = "SELECT food_num FROM food WHERE food_name = ?"; psmt =
		 * con.prepareStatement(foodnumsql); psmt.setString(1, addfood); rs =
		 * psmt.executeQuery();
		 * 
		 * if (rs.next()) { String Terminalsql =
		 * "INSERT INTO terminal (member_num, food_num) VALUES (?, ?)"; psmt =
		 * con.prepareStatement(Terminalsql); if (memberRs != null && memberRs.next()) {
		 * psmt.setString(1, memberRs.getString("user_num")); psmt.setString(2,
		 * rs.getString("food_num")); // terminal에 추가한 결과를 따로 저장 terminalResult =
		 * psmt.executeUpdate();
		 * 
		 * if (terminalResult == 1) { System.out.println("새로운 음식 추가가 잘 됨 첼시 보러 가자"); }
		 * else { System.out.println("음식 추가에 실패했어....."); } } } } else {
		 * System.out.println("음식 추가에 실패했습니다."); return terminalResult; } } } catch
		 * (SQLException e) { e.printStackTrace(); } return result; }
		 */
		
		public int Person_correction(String correction, String person) {
			int result = 0;
			try {
				String sql = "UPDATE member SET user_name  = ? WHERE user_name  = ?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, correction);
				psmt.setString(2, person);

				result = psmt.executeUpdate();
				if (result == 1) {
					System.out.println("업데이트에 성공했다 뉴캐슬 보러가자" + ", " + result);
				} else {
					System.out.println("업데이트가 되지 않았다 친구야");
				}
			}
			catch (Exception e) {
				e.printStackTrace(); 
			}
			return result;
		}
	}
		
