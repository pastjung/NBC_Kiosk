package kiosk;

// 상품
public class Goods extends Menu {
    public int price;  // 상품 가격
    
    // 재고
    // 옵션

    // 생성자
    Goods(String name, String description, int price){
        super(name, description);
        this. price = price;
    }
}