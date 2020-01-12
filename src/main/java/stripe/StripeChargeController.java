package stripecharge;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import io.github.cdimascio.dotenv.Dotenv;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

@RestController
@RequestMapping("/api")
public class StripeChargeController {

  @PostMapping("/charge")
  public ResponseEntity<String> createCharge(@RequestBody StripeCharge stripeCharge) {
    try {
      // for demonstrations sake, using .env file
      Dotenv dotenv = Dotenv.load();
      
      // creating the charge
      Stripe.apiKey = dotenv.get("SK_TEST_KEY");
      Charge charge = Charge.create(stripeCharge.getCharge());
      System.out.println(charge);
      return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    } catch (StripeException e) {
      e.printStackTrace();
      return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }
}
