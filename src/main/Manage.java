package main;

import java.util.List;
import java.util.Scanner;

import con_con.ManageDAO;
import con_con.ManageDTO;
import con_con.food;

public class Manage {

	public static void main(String[] args) {
		ManageDTO mdto = new ManageDTO();
		Scanner sc = new Scanner(System.in);
		ManageDAO dao = new ManageDAO();
		
		List<ManageDTO> alluserinfo = dao.AllUserInfo();
		for (ManageDTO Member : alluserinfo) {
			System.out.print("user_num : " + Member.getUser_num());
			System.out.print(", user_name : " + Member.getUser_name());
			System.out.println(", decision : " + Member.getDecision());
		}
		System.out.println("\n");
		List<ManageDTO> allfoodinfo = dao.AllfoodInfo();
		for (ManageDTO food : allfoodinfo) {
			System.out.print("food_num : " + food.getFood_num());
			System.out.println(", food_name : " + food.getFood_name());
		}
		
		System.out.println("\n위에 있는건 모든 DB정보임");

		System.out.println("\r\n" + "프로그램을 시작합니다...");
		// MemberDAO 겍체 생성
		food f = new food();

		// alluserinfo에 모든memberDTO 정보 넣고 반복문으로 모두다 출력하게 함.

		String select = null;
		// while문 돌리고 true 줘서 계속 반복하게 함.
		 ROOP: while (true) {
			System.out.println("\n메뉴을 선택해줄래? 1.유저 검색 2.유저 Y/N(복구/삭제) " + "3.사람 추가 4.음식삭제"
					+ " 5.유저가 좋아하는 음식 추가 6.유저 이름 수정 7.종료");
			// char(문자형) 타입으로 해서 유니코드나 '' 을 이용하고 charAt(0)를 이용해서 첫번째만 받아옴.
			select = sc.next();

			// 유니코드를 이용하여 콘솔창에 2를 입력하면 실행됨.
			if (select.equals("1")) {
				System.out.println("무엇을 검색하시겠습니까? 1번 사람 2번 음식");
				select = sc.next();
				if (select.equals("1")) {
					System.out.println("검색할 멤버의 이름을 입력하세요.");
					String memberName = sc.next();
					dao.searchMember(memberName);
					List<ManageDTO> list = dao.searchMember(memberName);
					for (ManageDTO dto : list) {
						System.out.println(" | " + dto.getUser_name() + " | " + dto.getFood_name() + " | ");
					}					
				}
				else if (select.equals("2")) {
					System.out.println("검색할 음식의 이름을 입력하세요.");
					String foodName = sc.next();
					dao.searchFood(foodName);
					List<ManageDTO> list = dao.searchFood(foodName);
					for (ManageDTO dto : list) {
						System.out.println(" | " + dto.getFood_name() + " | " + dto.getUser_name() + " | ");
					}
				}
				else {
					System.out.println("올바른 값을 입력해주면좋겠어.");
					break ROOP;
				}

			}
			// 유니코드를 이용하여 콘솔창에 3를 입력하면 실행됨.
			else if (select.equals("2")) {
				modroop: while (true) {
					System.out.println("무엇을 수정하실건가요 | 1 - 멤버 | 2 - 음식 |");
					select = sc.next();

					if (select.equals("1")) {
						System.out.println("\n[2] 복구(Y) 또는 삭제(N) 하실껀가요?");
						System.out.println("\n삭제하거나 복구 하실 Y/N 을 입력해주세요");
						String decision = sc.next();
						System.out.println("누구를 삭제하거나 복구 하실지 누군가에 이름을 써주세요");
						String USER_NAME = sc.next();
						int RD = dao.recovery_Delete(decision, USER_NAME);
					} else if (select.equals("2")) {
						f.Modfood();

						while (true) {
							System.out.print("계속 수정하시겠습니까? y/n: ");
							String yorn = sc.next();
							if ("y".equals(yorn)) {
								break;
							} else if ("n".equals(yorn)) {
								break modroop;
							} else {
								System.out.println("y 혹은 n을 입력해 주세요");
							}
						}
					} else {
						System.out.println("않되는대요");
						break modroop;
					}
				}
			}
			// 유니코드를 이용하여 콘솔창에 3를 입력하면 실행됨.
			else if (select.equals("3")) {
				insroop: while (true) {
					System.out.println("[3]무엇을 입력하실건가요 | 1 - 멤버 | 2 - 음식 |");
					select = sc.next();

					if (select.equals("1")) {
						// 멤버
						System.out.println("사람을 추가하실껀가요?");
						System.out.println("\n추가할 사람에 이름을 적어주세요");
						String UN = sc.next();
						int addPerson = dao.addPerson(UN);
					} else if (select.equals("2")) {
						f.insertFood();
						while (true) {
							System.out.print("계속 입력하시겠습니까? y/n: ");
							String yorn = sc.next();
							if ("y".equals(yorn)) {
								break;
							} else if ("n".equals(yorn)) {
								break insroop;
							} else {
								System.out.println("y 혹은 n을 입력해 주세요");
							}
						}
					} else {
						System.out.println("여기가어디오");
						break insroop;
					}
				}

			}
			//콘솔창에 5를 입력하면 실행됨.
			else if (select.equals("4")) {
				f.DeleteFood();
				while (true) {
					System.out.print("계속 삭제하시겠습니까? y/n: ");
					String yorn = sc.next();
					if ("y".equals(yorn)) {
						break;
					} else if ("n".equals(yorn)) {
						break;
					} else {
						System.out.println("y 혹은 n을 입력해 주세요");
					}

				}

			}
			
			/*
			 * else if(select.equals("5")) { System.out.println("[5]유저 이름을 적어주세요"); String
			 * user_addfood = sc.next(); System.out.println("유저가 좋아하는 음식을 적어주세요"); String
			 * addfood = sc.next(); int add_food = dao.user_addfood(user_addfood, addfood);
			 * 
			 * }
			 */
			
			else if(select.equals("6")) {
				System.out.println("[6]무슨 이름으로 수정하실지 적어주세요");
				String PERSON = sc.next();
				System.out.println("어떤 이름을 수정 할까요?");
				String correction = sc.next();
				int person_correction = dao.Person_correction(PERSON, correction );
			}
			

			//콘솔창에 6를 입력하면 실행되고 while문을 빠져나감.
			else if (select.equals("7")) {
				System.out.println("나중에 또 놀러오세요");
				break;   
			} 

		}
	}
}