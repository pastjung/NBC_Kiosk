package kiosk;

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
