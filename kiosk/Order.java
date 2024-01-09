package kiosk;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 주문 시스템
public class Order{
    Map<Goods, Integer> shopping_basket = new HashMap<>();  // 장바구니
    public int wating_number = 0;  // 대기 번호
    public int total_basket_amount = 0;    // 장바구니 가격

    // 장바구니에 상품 추가 : 1 ~ 4번 메뉴
    public void addGoods(Goods good, int count){
        if(!shopping_basket.containsKey(good)){
            // 해당 상품이 장바구니에 없는 경우
            shopping_basket.put(good, count);
        }
        else{
            // 해당 상품이 장바구니에 있는 경우
            shopping_basket.replace(good, shopping_basket.get(good) + count);
        }
    }

    Order(){}

    // 장바구니 내역 확인 후 주문 : 5번 메뉴
    public void orderBasket(){
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
        for(Goods good : shopping_basket.keySet()){
            // 상품 정보 찾기
            // Goods good = findGood(menuName);

            // 상품 정보 출력
            System.out.printf("%-20s| ", good.name);
            System.out.printf("%3d 개| ", shopping_basket.get(good));
            System.out.printf("%5d 원| ", shopping_basket.get(good) * good.price);
            System.out.println(good.description);

            // 구매 정보(임시)
            order_history += String.format("%-20s| %3d 개| %5d 원\n", good.name, shopping_basket.get(good), shopping_basket.get(good) * good.price);

            // 총 가격
            total_basket_amount += shopping_basket.get(good) * good.price;
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

    // 진행중인 주문 취소(장바구니 비우기) : 6번 메뉴
    public void cancelOrder(){
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
    public void changeBasket() {

    }
}