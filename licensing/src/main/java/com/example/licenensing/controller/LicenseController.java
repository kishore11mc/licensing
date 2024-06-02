package com.example.licenensing.controller;

import com.example.licensing.util.EncryptionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LicenseController {

	private final SecretKey secretKey;

	public LicenseController() throws Exception {
		this.secretKey = EncryptionUtil.getKey();
	}

	@GetMapping("/encrypt")
	public Map<String, String> encrypt(@RequestParam String macId) throws Exception {
		System.out.print("cau8ught0");
		String encrypted = EncryptionUtil.encrypt(macId, secretKey);
		Map<String, String> response = new HashMap<>();
		response.put("encryptedMacId", encrypted);
		response.put("key", EncryptionUtil.encodeKey(secretKey));
		return response;
	}

	@GetMapping("/decrypt")
	public String decrypt(@RequestParam String encryptedMacId, @RequestParam String key) throws Exception {
		SecretKey secretKey = EncryptionUtil.getKeyFromEncoded(key);
		return EncryptionUtil.decrypt(encryptedMacId, secretKey);
	}
}
