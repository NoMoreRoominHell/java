package com.algorithms.fourth.common;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.algorithms.fourth.pojo.User;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Jwt {

	public static final String SECRET = "eXAiOiJK1231OjF9.qw1_D";

	public static Algorithm algorithm = Algorithm.HMAC256(SECRET);

	public static <T> String sign(T object) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);
		Date date = calendar.getTime();
		return JWT.create().withIssuer("auth0").withClaim("obj", JSON.toJSONString(object)).withExpiresAt(date)
				.sign(algorithm);
	}

	public static <T> T unsign(String token, Class<T> classT) {
		JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
		try {
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> claims = jwt.getClaims();
			return JSON.parseObject(claims.get("obj").asString(), classT);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(10000L);
		user.setUserName("abc");
		user.setUserName("123");

		String token = Jwt.sign(user);
		System.out.println(token);
		User u = Jwt.unsign(token, User.class);
		if (u != null) {
			System.out.println(JSON.toJSONString(u));
		} else {
			System.out.println("token不合法");
		}
	}
}