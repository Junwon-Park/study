package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController // 이 애노테이션을 달아 놓으면 핸들러에서 반환하는 값이 뷰의 이름이 아니라 클라이언트에 반환할 텍스트로 인식해서 응답의 Body에 담아 반환한다.
// @Controller 애노테이션은 기본적으로 핸들러가 반환하는 텍스트를 뷰의 논리 이름으로 인식해서 뷰 리졸버에 해당 논리 이름을 담아 뷰를 생성해서 반환한다.
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/hello-basic", "hello-go"})
    // {}로 묶으면 여러 URL에 맵핑할 수 있다. -> 둘 중 어떤 URL로 들어와도 이 메서드가 호출된다.(value 옵션의 이름은 생략 가능하다. -> {"Path"}만 작성해도 동일하게 인식한다.
    // requestMethod 옵션에 HTTP 메서드를 설정하지 않으면 모든 HTTP 메서드를 허용한다.
    public String helloBasic() {
        log.info("helloBasic");

        return "ok";
    }

    @RequestMapping(value = "mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");

        return "ok";
    }

    @GetMapping("/mapping-get-v2")
    // GetMapping은 @RequestMapping 애노테이션을 포함하고 있으며, method 옵션을 RequestMethod.GET으로 설정한 것과 같다.
    // 위 mappingGetV1의 것 처럼 @RequestMapping(value = "mapping-get-v2", method = RequestMethod.GET)이라고 작성한 것과 동일하다.
    public String mappingGetV2() {
        log.info("mappingGetV2");

        return "ok";
    }


    @GetMapping("/mapping/{userId}") // 최근 API들은 리소스 Path에 식별자를 넣는 방식을 많이 사용한다.
    public String mappingPath(@PathVariable("userId") String data) { // @PathVariable을 받은 파라미터 이름이 Path variable의 이름과 동일하면 애노테이션의 이름을 생략할 수 있다. -> @PathVariable String userId
        log.info("mappingPath userId={}", data);

        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}") // PathVariable 다중 사용
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);

        return "ok";
    }

    /**
     * 지정한 파라미터로 추가 매핑
     * params = "mode"
     * params = "!mode" -> 파라미터에 mode라는 거싱 없어야 한다.
     * params = "mode=debug"
     * params = "mode!=debug" -> 파라미터의 mode가 debug이면 안된다.
     * params = {"mode=debug, "date=good"} // 파라미터에 mdoe=debug, date=good이 있어야 한다.
     * <p>
     * 하지만 요즘엔 URL에 고정된 파라마니터를 사용하기 보다는 PathVariable을 많이 사용하는 추세이다.
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    // 파라미터에 mode=debug라는 것이 있어야 한다. -> /mapping-param?mode=debug
    public String mappingParam() {
        log.info("mappingParam");

        return "ok";
    }

    /**
     * 지정한 파라미터로 추가 매핑
     * headers = "mode"
     * headers = "!mode" -> 헤더에 mode라는 거싱 없어야 한다.
     * headers = "mode=debug"
     * headers = "mode!=debug" -> 헤더의 mode가 debug이면 안된다.
     * headers = {"mode=debug, "date=good"} // 헤더에 mdoe=debug, date=good이 있어야 한다.
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");

        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes = "application/json"
     * consumes = "!application/json"
     * consumes = "application/*"
     * consumes = "*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json") // consumes의 값을 텍스트로 그냥 넣을 수도 있지만 Spring에서 제공하는 MediaType.APPLICATION_JSON_VALUE을 사용할 수도 있다. 오타 발생 방지를 위해 후자를 사용하는 것이 더 좋다.
    // consumes 옵션으로 이 API에서 제공하는 Content-Type이 무엇인지 지정할 수 있다.
    // 클라이언트 요청의 Content-Type 헤더의 값이 여기에 지정한 consumes와 일치해야 한다.
    // 클라이언트 요청의 Content-Type은 지정한 값의 Media Type의 결과를 응답해달라는 의미이다.
    // 옵션의 이름이 consumes(소비하다.)인 이유는 서버 입장에서 생산한 해당 Content-Type을 소비하는 입장이기 때문이다.
    // 만약 클라이언트 요청의 Content-Type 헤더의 값이 */*이라면 모든 Media Type을 허용한다는 의미이므로 consumes의 값이 무엇이든 동작한다.
    public String mappingConsumes() {
        log.info("mappingConsumes");

        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html") // 이 API가 생산하는 Media Type이 text/html이라고 지정한 것이다. 텍스트로 지정할 수도 있고 Spring이 제공하는 MediaType.TEXT_PLAIN_VALUE를 사용할 수도 있다. 오타 발생 방지를 위해 후자를 사용하는 것이 더 좋다.
    // 클라이언트 요청의 Accept 헤더의 값이 이 것과 일치해야 동작한다. -> 일치하지 않으면 406(Not Acceptable)을 반환한다.
    // 클라이언트 요청의 Accept 헤더에 지정한 Media Type만을 허용한다는 의미이다.
    // 만약 클라이언트 요청의 Accept 헤더의 값이 */*이라면 모든 Media Type을 허용한다는 의미이므로 produces의 값이 무엇이든 동작한다.
    public String mappingProduces() {
        log.info("mappingProduces");

        return "ok";
    }
}
