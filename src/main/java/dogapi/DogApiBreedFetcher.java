package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        //  Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        String apiURL = "https://dog.ceo/api/breed/" + breed + "/list";
        final Request request = new Request.Builder()
                .url(apiURL)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            if (!"success".equals(responseBody.getString("status"))){
                throw new BreedNotFoundException(breed);
            }
            else {
                final List<String> breedsArray = new ArrayList<>();
                for (int i = 0; i < responseBody.getJSONArray("message").length(); i++) {
                    breedsArray.add(i, responseBody.getJSONArray("message").getString(i));
                }
                return breedsArray;

            }
        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }

    }



    }