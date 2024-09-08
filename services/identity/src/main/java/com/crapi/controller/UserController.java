/*
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crapi.controller;

import com.crapi.config.JwtProvider;
import com.crapi.constant.UserMessage;
import com.crapi.model.CRAPIResponse;
import com.crapi.model.DashboardResponse;
import com.crapi.model.LoginForm;
import com.crapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/identity/api/v2/user")
public class UserController {

  @Autowired UserService userService;

  @Autowired JwtProvider tokenProvider;

  /**
   * @param request getting jwt token for user from request header
   * @return user object with the details of vehicle and profile by token email.
   */
  @GetMapping("/dashboard")
  public ResponseEntity<?> dashboard(HttpServletRequest request) {
    DashboardResponse userData = userService.getUserByRequestToken(request);
    if (userData != null) {
      return ResponseEntity.status(HttpStatus.OK).body(userData);
    } else
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new CRAPIResponse(UserMessage.EMAIL_NOT_REGISTERED, 404));
  }
  /**
   * DELETE AFTER TEST 
   */
 @GetMapping("/identity/api/v2/user/dashboard")
  public ResponseEntity<?> dashboard(HttpServletRequest request) {
    DashboardResponse userData = userService.getUserByRequestToken(request);
    if (userData != null) {
      return ResponseEntity.status(HttpStatus.OK).body(userData);
    } else
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new CRAPIResponse(UserMessage.EMAIL_NOT_REGISTERED, 404));
  }
  /**
   * @param loginForm contains email and updated password
   * @param request getting jwt token for user from request header
   * @return reset user password for the user. first verify token and then reset user password
   */
  @PostMapping("/reset-password")
  public ResponseEntity<CRAPIResponse> resetPassword(
      @RequestBody LoginForm loginForm, HttpServletRequest request)
      throws UnsupportedEncodingException {

    CRAPIResponse resetPasswordResponse = userService.resetPassword(loginForm, request);
    if (resetPasswordResponse != null && resetPasswordResponse.getStatus() == 200) {
      return ResponseEntity.ok().body(new CRAPIResponse(UserMessage.PASSWORD_GOT_RESET));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resetPasswordResponse);
  }
  /**
   * DELETE AFTER TEST
   * @param loginForm contains email and updated password
   * @param request getting jwt token for user from request header
   * @return reset user password for the user. first verify token and then reset user password
   */
  @PostMapping("/identity/api/v2/user/reset-password")
  public ResponseEntity<CRAPIResponse> resetPassword(
      @RequestBody LoginForm loginForm, HttpServletRequest request)
      throws UnsupportedEncodingException {

    CRAPIResponse resetPasswordResponse = userService.resetPassword(loginForm, request);
    if (resetPasswordResponse != null && resetPasswordResponse.getStatus() == 200) {
      return ResponseEntity.ok().body(new CRAPIResponse(UserMessage.PASSWORD_GOT_RESET));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resetPasswordResponse);
  }

   @PostMapping("/workshop/api/shop/products")
  public ResponseEntity<CRAPIResponse> resetPassword(
      @RequestBody LoginForm loginForm, HttpServletRequest request)
      throws UnsupportedEncodingException {

    CRAPIResponse resetPasswordResponse = userService.resetPassword(loginForm, request);
    if (resetPasswordResponse != null && resetPasswordResponse.getStatus() == 200) {
      return ResponseEntity.ok().body(new CRAPIResponse(UserMessage.PASSWORD_GOT_RESET));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resetPasswordResponse);
  }
  /**
   * DELETE THIS AFTER TEST
   */
  public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/workshop/api/shop/products")
    public ResponseEntity<CRAPIResponse<DashboardResponse>> getProducts(
            HttpServletRequest request,
            @RequestHeader(value = "accept-encoding", required = false) String acceptEncoding,
            @RequestHeader(value = "accept-language", required = false) String acceptLanguage,
            @RequestHeader(value = "x-volterra-truncated-hdr", required = false) String volterraTruncatedHdr
    ) {
        try {
            // TODO: Implement actual product retrieval logic
            List<Product> products = new ArrayList<>();
            products.add(new Product(1, "Product 1", "10.99", "http://example.com/image1.jpg"));
            products.add(new Product(2, "Product 2", "15.99", "http://example.com/image2.jpg"));

            DashboardResponse dashboardResponse = new DashboardResponse(products, 100.0);
            CRAPIResponse<DashboardResponse> response = new CRAPIResponse<>(UserMessage.SUCCESS_PRODUCTS_FETCH, dashboardResponse);

            return ResponseEntity.ok()
                    .header("Allow", "GET")
                    .header("Content-Type", "application/json")
                    .header("Referrer-Policy", "strict-origin-when-cross-origin")
                    .header("X-Volterra-Truncated-Hdr", volterraTruncatedHdr)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CRAPIResponse<>(UserMessage.INTERNAL_SERVER_ERROR, null));
        }
    }

    // Product class (consider moving to a separate file)
    private static class Product {
        private int id;
        private String name;
        private String price;
        private String imageUrl;

        public Product(int id, String name, String price, String imageUrl) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }
}
}
