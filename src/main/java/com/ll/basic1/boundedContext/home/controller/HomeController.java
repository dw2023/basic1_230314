package com.ll.basic1.boundedContext.home.controller;

import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

// @Controller 의 의미
// 개발자가 스프링부트에게 말한다.
// 아래 있는 HomeController 는 컨트롤러이다.
@Controller
public class HomeController {
    private int count;
    private final List<Person> people; // 왠만하면 final 다 붙이는 게 좋음 (실수예방)
    private final MemberService memberService; // 필드 주입 -> 생성자 주입 (변경)

    // @Autowired 생략가능
    public HomeController(MemberService memberService) {
        count = -1;
        people = new ArrayList<>();
        this.memberService = memberService;
    }

    // @GetMapping("/home/main") 의 의미
    // 개발자가 스프링부트에게 말한다.
    // 만약에 /home/main 이런 요청이 오면 아래 메서드를 실행해줘
    @GetMapping("/home/main")
    // @ResponseBody 의 의미
    // 아래 메서드를 실행한 후 그 리턴값을 응답으로 삼아줘
    @ResponseBody
    public String showMain() {
        return "안녕하세요.";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease() {
        count++;
        return count;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    // @RequestParam 의 의미
    // 개발자가 스프링부트에게 말한다.
    // int a 는 쿼리스트링에서 a 파라미터의 값을 얻은 후 정수화 한 값이어야 한다.
    // @RequestParam 는 생략가능
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("/home/returnBoolean")
    @ResponseBody
    public boolean showReturnBoolean() {
        return true;
    }

    @GetMapping("/home/returnDouble")
    @ResponseBody
    public double showReturnDouble() {
        return Math.PI;
    }

    @GetMapping("/home/returnIntArray")
    @ResponseBody
    public int[] showReturnIntArray() {
        int[] arr = new int[]{10, 20, 30};

        return arr;
    }

    @GetMapping("/home/returnIntList")
    @ResponseBody
    public List<Integer> showReturnIntList() {
        List<Integer> list = new ArrayList<>() {{
            add(10);
            add(20);
            add(30);
        }};

        return list;
    }

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
        Map<String, Object> map = new LinkedHashMap<>() {{ // LinkedHashMap은 순서대로 출력함
            put("id", 1);
            put("speed", 100);
            put("name", "카니발");
            put("relatedIds", new ArrayList<>() {{
                add(2);
                add(3);
                add(4);
            }});
        }};

        return map;
    }

    @GetMapping("/home/returnCar")
    @ResponseBody
    public Car showReturnCar() {
        Car car = new Car(1, 100, "카니발", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});

        return car;
    }

    @GetMapping("/home/returnCarV2")
    @ResponseBody
    public CarV2 showReturnCarV2() {
        CarV2 car = new CarV2(1, 100, "카니발", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});

        car.setName(car.getName() + "V2");

        return car;
    }

    @GetMapping("/home/returnCarMapList")
    @ResponseBody
    public List<Map<String, Object>> showReturnCarMapList() {
        Map<String, Object> carMap1 = new LinkedHashMap<>() {{
            put("id", 1);
            put("speed", 100);
            put("name", "카니발");
            put("relatedIds", new ArrayList<>() {{
                add(2);
                add(3);
                add(4);
            }});
        }};

        Map<String, Object> carMap2 = new LinkedHashMap<>() {{
            put("id", 2);
            put("speed", 200);
            put("name", "포르쉐");
            put("relatedIds", new ArrayList<>() {{
                add(5);
                add(6);
                add(7);
            }});
        }};

        List<Map<String, Object>> list = new ArrayList<>();

        list.add(carMap1);
        list.add(carMap2);

        return list;
    }

    @GetMapping("/home/returnCarList")
    @ResponseBody
    public List<CarV2> showReturnCarList() {
        CarV2 car1 = new CarV2(1, 100, "카니발", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});

        CarV2 car2 = new CarV2(2, 200, "포르쉐", new ArrayList<>() {{
            add(5);
            add(6);
            add(7);
        }});

        List<CarV2> list = new ArrayList<>();

        list.add(car1);
        list.add(car2);

        return list;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age) {
        Person p = new Person(name, age);

        System.out.println(p); // 콘솔에 출력해서 확인하기 위한 코드
        // @ToString을 Person 클래스에 달아줘야 문자열로 확인 가능해짐

        people.add(p);

        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id) {

        // person -> person.getId() == id
        // 위 함수가 참인 엘리먼트(요소) 경우가 존재하면, 해당 요소를 삭제한다.
        // removed 에는 삭제수행여부가 저장된다.
        // 조건에 맞는걸 찾았고 삭제까지 되었다면 true, 아니면 false
        boolean removed = people.removeIf(person -> person.getId() == id);
        /* stream 안쓴 버전
        for ( Person p : people ) {
            if ( p.getId() == id ) people.remove(p);
        }
         */

        if (removed == false) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }

        return "%d번 사람이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age) {
        Person found = people
                .stream()
                .filter(p -> p.getId() == id) // id와 일치하는 것만 필터링
                .findFirst()
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }

        found.setName(name);
        found.setAge(age);

        return "%d번 사람이 수정되었습니다.".formatted(id);
    }

    @GetMapping("/home/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        resp.getWriter().append("Hello, you are %d years old.".formatted(age));
    }

    // 쿠키를 사용해서 스프링부트가 브라우저를 구별해서 응답하도록 함
    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException { // 리턴되는 int 값은 String 화 되어서 고객(브라우저)에게 전달된다.
        int countInCookie = 0;

        // 고객이 가져온 쿠폰에서 count 쿠폰을 찾고 그 쿠폰의 값을 가져와서 정수화한다.
        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count")) // 이름이 count인 쿠폰 찾기
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0); // 위 모든 과정이 실패하면 0
        }

        int newCountInCookie = countInCookie + 1;

        // 고객이 가져온 count 쿠폰값에 1을 더한 쿠폰을 만들어서 고객에게 보낸다.
        // 쉽게 말하면 브라우저(고객)에 저장되어 있는 count 쿠폰의 값을 1 증가시킨다.
        // 이렇게 브라우저의 쿠키값을 변경하면 재방문시에 스프링부트가 다시 그 값을 받게 되어 있다.
        resp.addCookie(new Cookie("count", newCountInCookie + ""));

        // 응답 본문 리턴
        return newCountInCookie;
    }

    @GetMapping("/home/user1")
    @ResponseBody
    public Member showUser1() {
        return memberService.findByUsername("user1");
    }
}

    class Car {
        private final int id;
        private final int speed;
        private final String name;
        private final List<Integer> relatedIds;

        public Car(int id, int speed, String name, List<Integer> relatedIds) {
            this.id = id;
            this.speed = speed;
            this.name = name;
            this.relatedIds = relatedIds;
        }

        // getter메서드가 없으면 private이라 리턴안됨
        public int getId() {
            return id;
        }

        public int getSpeed() {
            return speed;
        }

        public String getName() {
            return name;
        }

        public List<Integer> getRelatedIds() {
            return relatedIds;
        }
    }

    // lombok - @AllArgsConstructor, @Getter, @Setter
    @AllArgsConstructor // 클래스 내부 모든 필드의 생성자 자동생성
    @Getter // 모든 필드의 getter 자동생성 (해당 필드 위에만 적으면 개별 생성)
    class CarV2 {
        private final int id;
        private final int speed;
        @Setter
        private String name;
        private final List<Integer> relatedIds;

        /* @AllArgsConstructor: 아래 코드 자동생성(생략가능)
        public Car(int id, int speed, String name, List<Integer> relatedIds) {
               this.id = id;
               this.speed = speed;
               this.name = name;
               this.relatedIds = relatedIds;
        }
        */
    }

    @AllArgsConstructor // 생성자1 (필드 선언된 순서대로 매개변수로 들어감)
    @Getter
    @ToString // ToString 메서드 생성
    class Person {
        private static int lastId; // Person 객체에서 연속적으로 기억되야 하므로 static
        private final int id;
        @Setter
        private String name;
        @Setter
        private int age;


        static {
            lastId = 0;
        }

        // 생성자2
        Person(String name, int age) {
            this(++lastId, name, age);
        }
    }
