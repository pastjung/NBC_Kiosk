import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

// 메뉴
class Menu{
    String name;    // 카테고리 이름
    String description; // 카테고리 설명

    // 생성자
    Menu(String name, String description){
        this.name = name;
        this.description = description;
    }
}

// 상품
class Goods extends Menu{
    int price;  // 상품 가격

    // 생성자
    Goods(String name, String description, int price){
        super(name, description);
        this. price = price;
    }
}

// 주문 시스템
class Order{
    Map<String, Integer> shopping_basket = new HashMap<>();   // 장바구니 배열
    int wating_number = 0;  // 대기 번호
    int total_basket_amount = 0;    // 장바구니 가격

    // 장바구니에 상품 추가 : 1 ~ 4번 메뉴
    void addGoods(String menuName, int count){
        if(!shopping_basket.containsKey(menuName)){
            // 해당 상품이 장바구니에 없는 경우
            shopping_basket.put(menuName, count);
        }
        else{
            // 해당 상품이 장바구니에 있는 경우
            shopping_basket.replace(menuName, shopping_basket.get(menuName) + count);
        }
    }

    Order(){}
    // 장바구니 내역 확인 후 주문 : 5번 메뉴
    void orderBasket(){
        if(shopping_basket.isEmpty()){
            System.out.println("상품을 추가해주세요");
            return;
        }

        Scanner input = new Scanner(System.in);

        String order_history = "";

        // 주문 확인 문구 출력
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[ Orders ]");

        // 장바구니 내역 출력
        for(String menuName : shopping_basket.keySet()){
            // 상품 정보 찾기
            Goods good = findGood(menuName);

            // 상품 정보 출력
            System.out.printf("%-20s| ", menuName);
            System.out.printf("%3d 개| ", shopping_basket.get(menuName));
            System.out.printf("%5d 원| ", shopping_basket.get(menuName) * good.price);
            System.out.println(good.description);

            // 구매 정보(임시)
            order_history += String.format("%-20s| %3d 개| %5d 원\n", menuName, shopping_basket.get(menuName), shopping_basket.get(menuName) * good.price);
            
            // 총 가격
            total_basket_amount += shopping_basket.get(menuName) * good.price;
        }
        System.out.println();

        // 총 주문 가격 출력
        System.out.println("[ Total ]");
        System.out.println(total_basket_amount + "원");
        System.out.print("1. 주문      2. 메뉴판\n> ");

        // 사용자 입력
        int menu = input.nextInt();
        input.nextLine();

        if(menu == 1){
            System.out.println("주문이 완료되었습니다!\n");

            // 주문 완료
            wating_number++;
            System.out.println("대기번호는 [ "+ wating_number + " ] 번 입니다.");
            System.out.println("(3초후 메뉴판으로 돌아갑니다.)");

            // 총 매출 추가
            MainSystem.revenue += total_basket_amount;
            
            // 장바구니 초기화
            total_basket_amount = 0;
            shopping_basket.clear();

            // 구매 정보 기록
            MainSystem.sales_particulars.add(order_history);

            // 3초 대기 시간
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) { }
        }
        else if(menu == 2){
            // 장바구니 초기화
            total_basket_amount = 0;
            shopping_basket.clear();
            System.out.println("메뉴판으로 돌아갑니다.");
        }
        else{
            System.out.println("잘못된 입력입니다.");
        }
    }
    
    // 상품이름을 이용해 정보 찾기
    Goods findGood(String menuName){
        for(Goods good : MainSystem.bugers){
            if (good.name.equals(menuName)) {
                return good;
            }
        }
        for(Goods good : MainSystem.forzen_custard){
            if (good.name.equals(menuName)) {
                return good;
            }
        }
        for(Goods good : MainSystem.drinks){
            if (good.name.equals(menuName)) {
                return good;
            }
        }
        for(Goods good : MainSystem.beer){
            if (good.name.equals(menuName)) {
                return good;
            }
        }
        return null;
    }

    // 진행중인 주문 취소(장바구니 비우기) : 6번 메뉴
    void cancelOrder(){
        if(shopping_basket.isEmpty()){
            System.out.println("장바구니가 비어있습니다.");
            return;
        }

        Scanner input = new Scanner(System.in);

        // 취소 확인 문구 출력
        System.out.println("\n진행하던 주문을 취소하시겠습니까?");
        System.out.print("1. 확인        2. 취소\n> ");

        // 사용자 입력 확인
        int cancel = input.nextInt();
        input.nextLine();

        if(cancel == 1){
            shopping_basket.clear();
            System.out.println("주문이 취소되었습니다.");
        }
        else if(cancel == 2){
            System.out.println("주문을 계속 해주세요");
        }
        else{
            System.out.println("잘못된 입력입니다.");
        }
    }

    // 장바구니 상품 수량 변경 : 5번 메뉴의 추가 기능
    void changeBasket() {

    }
}

// 전체 시스템
class MainSystem{
    static int  revenue = 0;   // 매출액
    static ArrayList<String> sales_particulars = new ArrayList<String>(); // 판매 내역

    // [ SHAKESHACK MENU ]
    static Menu[] menu = {
        new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거"),
        new Menu("Forzen Custard", "매장에서 신선하게 만드는 아이스크림"),
        new Menu("Drinks", "매장에서 직접 만드는 음료"),
        new Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"),
    };

    // 1. Burgers
    static Goods[] bugers = {
        new Goods("ShackBurger", "토마토, 양상추, 쉑소스가 토핑된 치즈버거", 6900),
        new Goods("SmokeShack", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", 8900),
        new Goods("Shroom Burger", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거", 9400),
        new Goods("Cheeseburger", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", 6900),
        new Goods("Hamburger", "비프패티를 기반으로 야채가 들어간 기본버거", 5400),
    };

    // 2. Forzen Custard
    static Goods[] forzen_custard = {
        new Goods("Choco Icream", "초코맛 수제 아이스크림", 2500),
        new Goods("Banana Icream", "바닐라맛 수제  아이스크림", 2500),
        new Goods("Strawberry Icream", "딸기맛 수제 아이스크림", 2500),
    };


    // 3. Drinks
    static Goods[] drinks = {
        new Goods("Soda", "소다", 2000),
        new Goods("Sprite", "사이다", 2000),
        new Goods("Cola", "콜라", 2000),
    };


    // 4. Beer
    static Goods[] beer = {
        new Goods("Ail", "기본적인 에일", 5000),
        new Goods("Pale Ale", "페일 에일", 7000),
        new Goods("LARGER", "라거", 8000),
    };

    // 주문 클래스
    Order order = new Order();

    // 생성자
    MainSystem(){}

    // 시스템 실행 함수 : 반복문 돌면서 계속 수행하기
    void startSystem(){
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
    void printMenu(){
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
    void printGood(Goods good){
        System.out.printf("%-20s| ", good.name);
        System.out.printf("%5d 원| ", good.price);
        System.out.println(good.description);
    }

    // 메뉴 선택
    void selectGood(Goods[] good){
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
            order.addGoods(good[sub_menu-1].name, 1);
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
    void checkTotalSales(){
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

/*
* 추가 구현할 선택사항
* 1. Order.changeBasket() : 장바구니 상품 개수 변경 메서드
* 2. MainSystem.selectGood()
*   - 상품 옵션 선택 기능
*   - 상품 수량 선택 기능
 */

public class Main {
    public static void main(String[] args) {
        MainSystem main_system = new MainSystem();
        main_system.startSystem();
    }
}
