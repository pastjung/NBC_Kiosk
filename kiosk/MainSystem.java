package kiosk;

import java.util.ArrayList;
import java.util.Scanner;

// 전체 시스템
public class MainSystem{
    public static int  revenue = 0;   // 매출액
    public static ArrayList<String> sales_particulars = new ArrayList<>(); // 판매 내역

    // [ SHAKESHACK MENU ]
    public static Menu[] menu = {
        new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거"),
        new Menu("Forzen Custard", "매장에서 신선하게 만드는 아이스크림"),
        new Menu("Drinks", "매장에서 직접 만드는 음료"),
        new Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"),
    };

    // 1. Burgers
    public static Goods[] bugers = {
        new Goods("ShackBurger", "토마토, 양상추, 쉑소스가 토핑된 치즈버거", 6900),
        new Goods("SmokeShack", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", 8900),
        new Goods("Shroom Burger", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거", 9400),
        new Goods("Cheeseburger", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", 6900),
        new Goods("Hamburger", "비프패티를 기반으로 야채가 들어간 기본버거", 5400),
    };

    // 2. Forzen Custard
    public static Goods[] forzen_custard = {
        new Goods("Choco Icream", "초코맛 수제 아이스크림", 2500),
        new Goods("Banana Icream", "바닐라맛 수제  아이스크림", 2500),
        new Goods("Strawberry Icream", "딸기맛 수제 아이스크림", 2500),
    };


    // 3. Drinks
    public static Goods[] drinks = {
        new Goods("Soda", "소다", 2000),
        new Goods("Sprite", "사이다", 2000),
        new Goods("Cola", "콜라", 2000),
    };


    // 4. Beer
    public static Goods[] beer = {
        new Goods("Ail", "기본적인 에일", 5000),
        new Goods("Pale Ale", "페일 에일", 7000),
        new Goods("LARGER", "라거", 8000),
    };

    // 주문 클래스
    Order order = new Order();

    // 생성자
    MainSystem(){}

    // 시스템 실행 함수 : 반복문 돌면서 계속 수행하기
    public void startSystem(){
        Scanner input = new Scanner(System.in);
        while(true){
            printMenu();

            System.out.print("> ");
            int menu = input.nextInt();
            input.nextLine();

            if( 0 < menu && menu <=4){
                System.out.println("\n\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"");
                System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.\n");

                // 1번 메뉴
                if(menu == 1){
                    selectGood(bugers);
                }
                // 2번 메뉴
                else if(menu == 2){
                    selectGood(forzen_custard);
                }
                // 3번 메뉴
                else if(menu == 3){
                    selectGood(drinks);
                }
                // 4번 메뉴
                else{
                    selectGood(beer);
                }
            }
            // 5번 메뉴
            else if(menu == 5){
                order.orderBasket();
            }
            // 6번 메뉴
            else if(menu == 6){
                order.cancelOrder();
            }
            // 0번 메뉴
            else if(menu == 0){
                checkTotalSales();
            }
            else if(menu == -1){
                break;
            }
            else{
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 메뉴판 출력
    public void printMenu(){
        System.out.println("\n\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");

        // 음식 메뉴
        System.out.println("[ SHAKESHACK MENU ]");
        for(int i = 0; i < menu.length; i++){
            System.out.print((i+1) + ". ");
            System.out.printf("%-20s| ", menu[i].name);
            System.out.println(menu[i].description);
        }

        // 주문 옵션
        System.out.println("\n[ ORDER MENU ]");
        System.out.println("5. Order       | 장바구니를 확인 후 주문합니다.");
        System.out.println("6. Cancel      | 진행중인 주문을 취소합니다.");
    }

    // 상품의 정보 출력
    public void printGood(Goods good){
        System.out.printf("%-20s| ", good.name);
        System.out.printf("%5d 원| ", good.price);
        System.out.println(good.description);
    }

    // 메뉴 선택
    public void selectGood(Goods[] good){
        Scanner input = new Scanner(System.in);

        // 메뉴 출력
        System.out.println("[ Burgers MENU ]");
        for(int i = 1; i <= good.length; i++){
            System.out.print(i+". ");
            printGood(good[i-1]);
        }
        System.out.print("> ");

        // 상품 선택
        int sub_menu = input.nextInt(); // 1번 ~ 4번 메뉴에서 상품 선택 메뉴
        input.nextLine();

        /*

        상품 옵션 선택 기능

         */

        /*

        상품 수량 선택 기능

         */

        // 상품 추가 확인
        System.out.println();
        printGood(good[sub_menu-1]);
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        System.out.print("> ");

        // 메뉴 확인
        int check_menu = input.nextInt();
        input.nextLine();

        // 장바구니에 추가 or 취소
        if(check_menu == 1){
            // 장바구니에 추가
            order.addGoods(good[sub_menu-1], 1);
            System.out.println("장바구니에 추가했습니다.");
        }
        else if(check_menu == 2){
            // 취소
            System.out.println("취소했습니다.");
        }
        else{
            System.out.println("잘못된 입력입니다.");
        }
    }

    // 전체 매출 & 판매 상품 목록 확인 : 0번 메뉴
    public void checkTotalSales(){
        Scanner input = new Scanner(System.in);

        // 총 판매 금액
        System.out.println("\n[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 금액은 [ " + revenue + "원  ] 입니다.\n");

        // 판매 상품 내역
        System.out.println("[ 총 판매상품 목록 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.\n");
        for(String goods : sales_particulars){
            System.out.println(goods);
        }

        System.out.println("1. 돌아가기");

        // 아무값을 입력하든 메뉴판으로 이동
        int menu = input.nextInt();
        input.nextLine();
    }
}