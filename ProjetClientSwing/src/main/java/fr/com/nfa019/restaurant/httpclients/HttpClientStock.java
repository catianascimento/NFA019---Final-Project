package fr.com.nfa019.restaurant.httpclients;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientStock {
	String password;
	String login;

	public HttpClientStock(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/stocks";

	public List<StockProduitBean> getAllStocks()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<StockProduitBean> Stocks = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<StockProduitBean>>() {
				});
		Stocks.forEach(Stock -> System.out.println(Stock.toString()));
		return Stocks;
	}

	public List<StockProduitBean> getAllExpiredStocks()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL + "/expireds";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<StockProduitBean> Stocks = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<StockProduitBean>>() {
				});
		Stocks.forEach(Stock -> System.out.println(Stock.toString()));
		return Stocks;
	}

	public List<ProduitBean> getAllProduitsFromStocks(Long stockId)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL + "/" + stockId + "/produits";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<ProduitBean> produits = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<ProduitBean>>() {
				});
		produits.forEach(produit -> System.out.println(produit.toString()));
		return produits;
	}

	// sending request to retrieve all the Stocks available.
	public int saveStock(StockProduitBean stock)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var StockJson = JSONUtils.convertFromObjectToJson(stock);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(StockJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request to retrieve all the Stocks available.
	public int saveStockProduit(StockProduitBean stockProduit)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var stockProduitJson = JSONUtils.convertFromObjectToJson(stockProduit);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(stockProduitJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Stock based on the StockId
	public void getStockDetailsById(Integer id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500)
			System.out.println("Stock Not Avaialble");
		else {
			StockProduitBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), StockProduitBean.class);
			System.out.println(bean);
		}
	}

	// send request to update the Stock details.
	public void updateStock(Long id, StockProduitBean Stock)
			throws InterruptedException, ExecutionException, IOException {
		var StockJson = JSONUtils.convertFromObjectToJson(Stock);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(StockJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to update the Stock details.
	public void updateStockProduit(Long id, StockProduitBean stockProduit)
			throws InterruptedException, ExecutionException, IOException {
		var stockProduitJson = JSONUtils.convertFromObjectToJson(stockProduit);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(stockProduitJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Stock by its StockId
	public void deleteStock(Long id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
