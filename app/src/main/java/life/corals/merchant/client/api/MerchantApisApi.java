package life.corals.merchant.client.api;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiClient;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.ApiResponse;
import life.corals.merchant.client.Configuration;
import life.corals.merchant.client.Pair;
import life.corals.merchant.client.ProgressRequestBody;
import life.corals.merchant.client.ProgressResponseBody;
import life.corals.merchant.client.model.Body;
import life.corals.merchant.client.model.Body1;
import life.corals.merchant.client.model.Body10;
import life.corals.merchant.client.model.Body11;
import life.corals.merchant.client.model.Body12;
import life.corals.merchant.client.model.Body13;
import life.corals.merchant.client.model.Body14;
import life.corals.merchant.client.model.Body15;
import life.corals.merchant.client.model.Body16;
import life.corals.merchant.client.model.Body18;
import life.corals.merchant.client.model.Body19;
import life.corals.merchant.client.model.Body20;
import life.corals.merchant.client.model.Body21;
import life.corals.merchant.client.model.Body22;
import life.corals.merchant.client.model.Body23;
import life.corals.merchant.client.model.Body24;
import life.corals.merchant.client.model.Body25;
import life.corals.merchant.client.model.Body26;
import life.corals.merchant.client.model.Body27;
import life.corals.merchant.client.model.Body28;
import life.corals.merchant.client.model.Body30;
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.InlineResponse200;
import life.corals.merchant.client.model.InlineResponse2001;
import life.corals.merchant.client.model.InlineResponse20010;
import life.corals.merchant.client.model.InlineResponse20011;
import life.corals.merchant.client.model.InlineResponse20012;
import life.corals.merchant.client.model.InlineResponse20013;
import life.corals.merchant.client.model.InlineResponse20014;
import life.corals.merchant.client.model.InlineResponse20015;
import life.corals.merchant.client.model.InlineResponse20016;
import life.corals.merchant.client.model.InlineResponse20017;
import life.corals.merchant.client.model.InlineResponse20018;
import life.corals.merchant.client.model.InlineResponse20019;
import life.corals.merchant.client.model.InlineResponse2008;
import life.corals.merchant.client.model.InlineResponse2009;
import life.corals.merchant.client.model.InlineResponseInsigts;
import life.corals.merchant.client.model.InlineResponseSummary;
import life.corals.merchant.client.model.PlanSuccessResponse;
import life.corals.merchant.client.model.RedeemVoucherListResponse;
import life.corals.merchant.client.model.SetUpVoucherList;
import life.corals.merchant.client.model.SetUpVoucherResponse;
import life.corals.merchant.client.model.VoucherManageBody;
import life.corals.merchant.client.model.VoucherManageResponse;

public class MerchantApisApi {
    private ApiClient apiClient;

    public MerchantApisApi() {
        this(Configuration.getDefaultApiClient());
    }

    public MerchantApisApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for genericCampaignPpcFetchPost
     * @param body na (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericCampaignPpcFetchPostCall(Body12 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/campaign/ppc/fetch";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericCampaignPpcFetchPostValidateBeforeCall(Body12 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericCampaignPpcFetchPost(Async)");
        }


        com.squareup.okhttp.Call call = genericCampaignPpcFetchPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * fetch list of active campaigns for merchant
     * Register new merchant device after manual onboarding.  This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @return InlineResponse20010
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20010 genericCampaignPpcFetchPost(Body12 body) throws ApiException {
        ApiResponse<InlineResponse20010> resp = genericCampaignPpcFetchPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * fetch list of active campaigns for merchant
     * Register new merchant device after manual onboarding.  This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @return ApiResponse&lt;InlineResponse20010&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20010> genericCampaignPpcFetchPostWithHttpInfo(Body12 body) throws ApiException {
        com.squareup.okhttp.Call call = genericCampaignPpcFetchPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20010>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * fetch list of active campaigns for merchant (asynchronously)
     * Register new merchant device after manual onboarding.  This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericCampaignPpcFetchPostAsync(Body12 body, final ApiCallback<InlineResponse20010> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericCampaignPpcFetchPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20010>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericCbpointsGethistoryPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericCbpointsGethistoryPostCall(Body24 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/cbpoints/gethistory";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericCbpointsGethistoryPostValidateBeforeCall(Body24 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = genericCbpointsGethistoryPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieve points transaction history
     * Points transaction history all customers or single customer
     * @param body body (optional)
     * @return InlineResponse20018
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20018 genericCbpointsGethistoryPost(Body24 body) throws ApiException {
        ApiResponse<InlineResponse20018> resp = genericCbpointsGethistoryPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Retrieve points transaction history
     * Points transaction history all customers or single customer
     * @param body body (optional)
     * @return ApiResponse&lt;InlineResponse20018&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20018> genericCbpointsGethistoryPostWithHttpInfo(Body24 body) throws ApiException {
        com.squareup.okhttp.Call call = genericCbpointsGethistoryPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20018>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve points transaction history (asynchronously)
     * Points transaction history all customers or single customer
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericCbpointsGethistoryPostAsync(Body24 body, final ApiCallback<InlineResponse20018> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericCbpointsGethistoryPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20018>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericNpsPost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericNpsPostCall(Body20 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/nps";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericNpsPostValidateBeforeCall(Body20 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericNpsPost(Async)");
        }


        com.squareup.okhttp.Call call = genericNpsPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Submit NPS scope (Star count)
     * Submit selected star count. callertype&#x3D; c or m. count &#x3D; 1 to 5
     * @param body body (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void genericNpsPost(Body20 body) throws ApiException {
        genericNpsPostWithHttpInfo(body);
    }

    /**
     * Submit NPS scope (Star count)
     * Submit selected star count. callertype&#x3D; c or m. count &#x3D; 1 to 5
     * @param body body (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> genericNpsPostWithHttpInfo(Body20 body) throws ApiException {
        com.squareup.okhttp.Call call = genericNpsPostValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * Submit NPS scope (Star count) (asynchronously)
     * Submit selected star count. callertype&#x3D; c or m. count &#x3D; 1 to 5
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericNpsPostAsync(Body20 body, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericNpsPostValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for genericOnloadPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericOnloadPostCall(Body10 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/onload";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericOnloadPostValidateBeforeCall(Body10 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = genericOnloadPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Call when Merchant or Customer launches App each time
     * This api is to pre-fetch config parameters for the mobile . callertype &#x3D; c or m
     * @param body body (optional)
     * @return InlineResponse2008
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse2008 genericOnloadPost(Body10 body) throws ApiException {
        ApiResponse<InlineResponse2008> resp = genericOnloadPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Call when Merchant or Customer launches App each time
     * This api is to pre-fetch config parameters for the mobile . callertype &#x3D; c or m
     * @param body body (optional)
     * @return ApiResponse&lt;InlineResponse2008&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse2008> genericOnloadPostWithHttpInfo(Body10 body) throws ApiException {
        com.squareup.okhttp.Call call = genericOnloadPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse2008>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Call when Merchant or Customer launches App each time (asynchronously)
     * This api is to pre-fetch config parameters for the mobile . callertype &#x3D; c or m
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericOnloadPostAsync(Body10 body, final ApiCallback<InlineResponse2008> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericOnloadPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse2008>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericOutletGetlistPost
     * @param body na (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericOutletGetlistPostCall(Body11 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/outlet/getlist";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericOutletGetlistPostValidateBeforeCall(Body11 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericOutletGetlistPost(Async)");
        }


        com.squareup.okhttp.Call call = genericOutletGetlistPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieve outlet list. (Merchant and Customer)
     * Register new merchant device after manual onboarding. This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @return InlineResponse2009
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse2009 genericOutletGetlistPost(Body11 body) throws ApiException {
        ApiResponse<InlineResponse2009> resp = genericOutletGetlistPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Retrieve outlet list. (Merchant and Customer)
     * Register new merchant device after manual onboarding. This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @return ApiResponse&lt;InlineResponse2009&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse2009> genericOutletGetlistPostWithHttpInfo(Body11 body) throws ApiException {
        com.squareup.okhttp.Call call = genericOutletGetlistPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse2009>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve outlet list. (Merchant and Customer) (asynchronously)
     * Register new merchant device after manual onboarding. This can be first merchant or subsequent employee onboarding
     * @param body na (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericOutletGetlistPostAsync(Body11 body, final ApiCallback<InlineResponse2009> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericOutletGetlistPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse2009>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericPntsRedeemPost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericPntsRedeemPostCall(Body22 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/pnts/redeem";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericPntsRedeemPostValidateBeforeCall(Body22 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericPntsRedeemPost(Async)");
        }


        com.squareup.okhttp.Call call = genericPntsRedeemPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * points redeem
     * none
     * @param body body (required)
     * @return InlineResponse20016
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20016 genericPntsRedeemPost(Body22 body) throws ApiException {
        ApiResponse<InlineResponse20016> resp = genericPntsRedeemPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * points redeem
     * none
     * @param body body (required)
     * @return ApiResponse&lt;InlineResponse20016&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20016> genericPntsRedeemPostWithHttpInfo(Body22 body) throws ApiException {
        com.squareup.okhttp.Call call = genericPntsRedeemPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20016>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * points redeem (asynchronously)
     * none
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericPntsRedeemPostAsync(Body22 body, final ApiCallback<InlineResponse20016> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericPntsRedeemPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20016>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericTokenGeneratePost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericTokenGeneratePostCall(Body19 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/token/generate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericTokenGeneratePostValidateBeforeCall(Body19 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericTokenGeneratePost(Async)");
        }


        com.squareup.okhttp.Call call = genericTokenGeneratePostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Generate token for a transaction request
     * none
     * @param body body (required)
     * @return InlineResponse20014
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20014 genericTokenGeneratePost(Body19 body) throws ApiException {
        ApiResponse<InlineResponse20014> resp = genericTokenGeneratePostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Generate token for a transaction request
     * none
     * @param body body (required)
     * @return ApiResponse&lt;InlineResponse20014&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20014> genericTokenGeneratePostWithHttpInfo(Body19 body) throws ApiException {
        com.squareup.okhttp.Call call = genericTokenGeneratePostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20014>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Generate token for a transaction request (asynchronously)
     * none
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericTokenGeneratePostAsync(Body19 body, final ApiCallback<InlineResponse20014> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericTokenGeneratePostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20014>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericTransactionsFetchPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericTransactionsFetchPostCall(Body15 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/transactions/fetch";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericTransactionsFetchPostValidateBeforeCall(Body15 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericTransactionsFetchPost(Async)");
        }


        com.squareup.okhttp.Call call = genericTransactionsFetchPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Common API to fetch transaction details for Customer and merchant
     * Path parameter-&gt; c when called from customer device and m when called from Merchant device&lt;br&gt; Request dates- For today transaction, Fromdate and To date are same.&lt;br&gt; For Past date transaction Fromdate and todate should not be more than 40 days.&lt;br&gt;Customerid is mandatory only if called by customer mobile&lt;br&gt; Fetch data from tbl_cust_txn_ledger. callertype-c or m
     * @param body none (required)
     * @return InlineResponse20012
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20012 genericTransactionsFetchPost(Body15 body) throws ApiException {
        ApiResponse<InlineResponse20012> resp = genericTransactionsFetchPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Common API to fetch transaction details for Customer and merchant
     * Path parameter-&gt; c when called from customer device and m when called from Merchant device&lt;br&gt; Request dates- For today transaction, Fromdate and To date are same.&lt;br&gt; For Past date transaction Fromdate and todate should not be more than 40 days.&lt;br&gt;Customerid is mandatory only if called by customer mobile&lt;br&gt; Fetch data from tbl_cust_txn_ledger. callertype-c or m
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponse20012&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20012> genericTransactionsFetchPostWithHttpInfo(Body15 body) throws ApiException {
        com.squareup.okhttp.Call call = genericTransactionsFetchPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20012>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Common API to fetch transaction details for Customer and merchant (asynchronously)
     * Path parameter-&gt; c when called from customer device and m when called from Merchant device&lt;br&gt; Request dates- For today transaction, Fromdate and To date are same.&lt;br&gt; For Past date transaction Fromdate and todate should not be more than 40 days.&lt;br&gt;Customerid is mandatory only if called by customer mobile&lt;br&gt; Fetch data from tbl_cust_txn_ledger. callertype-c or m
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericTransactionsFetchPostAsync(Body15 body, final ApiCallback<InlineResponse20012> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericTransactionsFetchPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20012>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericUpdatepinPost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericUpdatepinPostCall(Body18 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/updatepin";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "image/png"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericUpdatepinPostValidateBeforeCall(Body18 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericUpdatepinPost(Async)");
        }


        com.squareup.okhttp.Call call = genericUpdatepinPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Change PIN (API for both merchant and Customer)
     * send old and new pin values.&lt;br&gt; Validate old pin value before updating the new PIN. callertype &#x3D; c or m
     * @param body body (required)
     * @return java.io.File
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public java.io.File genericUpdatepinPost(Body18 body) throws ApiException {
        ApiResponse<java.io.File> resp = genericUpdatepinPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Change PIN (API for both merchant and Customer)
     * send old and new pin values.&lt;br&gt; Validate old pin value before updating the new PIN. callertype &#x3D; c or m
     * @param body body (required)
     * @return ApiResponse&lt;java.io.File&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<java.io.File> genericUpdatepinPostWithHttpInfo(Body18 body) throws ApiException {
        com.squareup.okhttp.Call call = genericUpdatepinPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<java.io.File>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Change PIN (API for both merchant and Customer) (asynchronously)
     * send old and new pin values.&lt;br&gt; Validate old pin value before updating the new PIN. callertype &#x3D; c or m
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericUpdatepinPostAsync(Body18 body, final ApiCallback<java.io.File> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericUpdatepinPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<java.io.File>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for insightsMonthPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call insightsMonthPostCall(Body28 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/insight/month";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call insightsMonthPostValidateBeforeCall(Body28 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling insightsMonthPost(Async)");
        }


        com.squareup.okhttp.Call call = insightsMonthPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Merchant API to fetch monthly  details for merchant
     * Merchant API to fetch details about merchant activies in last 30 days
     * @param body none (required)
     * @return InlineResponseInsigts
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponseInsigts insightsMonthPost(Body28 body) throws ApiException {
        ApiResponse<InlineResponseInsigts> resp = insightsMonthPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Merchant API to fetch monthly  details for merchant
     * Merchant API to fetch details about merchant activies in last 30 days
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponseInsigts&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponseInsigts> insightsMonthPostWithHttpInfo(Body28 body) throws ApiException {
        com.squareup.okhttp.Call call = insightsMonthPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponseInsigts>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Merchant API to fetch monthly  details for merchant (asynchronously)
     * Merchant API to fetch details about merchant activies in last 30 days
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call insightsMonthPostAsync(Body28 body, final ApiCallback<InlineResponseInsigts> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = insightsMonthPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponseInsigts>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for insightsSummaryPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call insightsSummaryPostCall(Body16 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/insight/summary";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call insightsSummaryPostValidateBeforeCall(Body16 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling insightsSummaryPost(Async)");
        }


        com.squareup.okhttp.Call call = insightsSummaryPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Merchant API to fetch customer insights summary
     * Merchant API to fetch details about merchant customer insgights summary
     * @param body none (required)
     * @return InlineResponseSummary
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponseSummary insightsSummaryPost(Body16 body) throws ApiException {
        ApiResponse<InlineResponseSummary> resp = insightsSummaryPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Merchant API to fetch customer insights summary
     * Merchant API to fetch details about merchant customer insgights summary
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponseSummary&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponseSummary> insightsSummaryPostWithHttpInfo(Body16 body) throws ApiException {
        com.squareup.okhttp.Call call = insightsSummaryPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponseSummary>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Merchant API to fetch customer insights summary (asynchronously)
     * Merchant API to fetch details about merchant customer insgights summary
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call insightsSummaryPostAsync(Body16 body, final ApiCallback<InlineResponseSummary> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = insightsSummaryPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponseSummary>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for insightsWeekPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call insightsWeekPostCall(Body28 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/insight/week";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call insightsWeekPostValidateBeforeCall(Body28 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling insightsWeekPost(Async)");
        }


        com.squareup.okhttp.Call call = insightsWeekPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Merchant API to fetch weekly  details for merchant
     * Merchant API to fetch details about merchant activies in last seven days
     * @param body none (required)
     * @return InlineResponseInsigts
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponseInsigts insightsWeekPost(Body28 body) throws ApiException {
        ApiResponse<InlineResponseInsigts> resp = insightsWeekPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Merchant API to fetch weekly  details for merchant
     * Merchant API to fetch details about merchant activies in last seven days
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponseInsigts&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponseInsigts> insightsWeekPostWithHttpInfo(Body28 body) throws ApiException {
        com.squareup.okhttp.Call call = insightsWeekPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponseInsigts>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Merchant API to fetch weekly  details for merchant (asynchronously)
     * Merchant API to fetch details about merchant activies in last seven days
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call insightsWeekPostAsync(Body28 body, final ApiCallback<InlineResponseInsigts> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = insightsWeekPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponseInsigts>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantCampaignPpcUpdateMPost
     * @param body All request data from client is required to create/update the record (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantCampaignPpcUpdateMPostCall(Body13 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/campaign/ppc/update/m";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantCampaignPpcUpdateMPostValidateBeforeCall(Body13 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantCampaignPpcUpdateMPost(Async)");
        }


        com.squareup.okhttp.Call call = merchantCampaignPpcUpdateMPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Create / Update pre-paid card campaign.
     * This API is called to create or overwrite the existing set up of pre-paid card campaign details.&lt;br&gt;This API is used to create new campaign too.&lt;br&gt; Ensure user acceptance before calling this API.
     * @param body All request data from client is required to create/update the record (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void merchantCampaignPpcUpdateMPost(Body13 body) throws ApiException {
        merchantCampaignPpcUpdateMPostWithHttpInfo(body);
    }

    /**
     * Create / Update pre-paid card campaign.
     * This API is called to create or overwrite the existing set up of pre-paid card campaign details.&lt;br&gt;This API is used to create new campaign too.&lt;br&gt; Ensure user acceptance before calling this API.
     * @param body All request data from client is required to create/update the record (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> merchantCampaignPpcUpdateMPostWithHttpInfo(Body13 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantCampaignPpcUpdateMPostValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * Create / Update pre-paid card campaign. (asynchronously)
     * This API is called to create or overwrite the existing set up of pre-paid card campaign details.&lt;br&gt;This API is used to create new campaign too.&lt;br&gt; Ensure user acceptance before calling this API.
     * @param body All request data from client is required to create/update the record (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantCampaignPpcUpdateMPostAsync(Body13 body, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantCampaignPpcUpdateMPostValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for merchantCbissuePost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantCbissuePostCall(Body21 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/cbissue";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantCbissuePostValidateBeforeCall(Body21 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantCbissuePost(Async)");
        }


        com.squareup.okhttp.Call call = merchantCbissuePostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * issue cashback points
     * NA
     * @param body body (required)
     * @return InlineResponse20015
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20015 merchantCbissuePost(Body21 body) throws ApiException {
        ApiResponse<InlineResponse20015> resp = merchantCbissuePostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * issue cashback points
     * NA
     * @param body body (required)
     * @return ApiResponse&lt;InlineResponse20015&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20015> merchantCbissuePostWithHttpInfo(Body21 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantCbissuePostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20015>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * issue cashback points (asynchronously)
     * NA
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantCbissuePostAsync(Body21 body, final ApiCallback<InlineResponse20015> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantCbissuePostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20015>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantCreateSchedulenotificationPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantCreateSchedulenotificationPostCall(Body25 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/notification/create";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantCreateSchedulenotificationPostValidateBeforeCall(Body25 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = merchantCreateSchedulenotificationPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * send notification for different types of customers
     * send notification for group or all customers with schudled time
     * @param body body (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void merchantCreateSchedulenotificationPost(Body25 body) throws ApiException {
        merchantCreateSchedulenotificationPostWithHttpInfo(body);
    }

    /**
     * send notification for different types of customers
     * send notification for group or all customers with schudled time
     * @param body body (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> merchantCreateSchedulenotificationPostWithHttpInfo(Body25 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantCreateSchedulenotificationPostValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * send notification for different types of customers (asynchronously)
     * send notification for group or all customers with schudled time
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantCreateSchedulenotificationPostAsync(Body25 body, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantCreateSchedulenotificationPostValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for merchantCustomersListPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantCustomersListPostCall(Body16 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/customers/list";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantCustomersListPostValidateBeforeCall(Body16 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantCustomersListPost(Async)");
        }


        com.squareup.okhttp.Call call = merchantCustomersListPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieve list of customers enrolled with Merchant
     * none
     * @param body none (required)
     * @return InlineResponse20013
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20013 merchantCustomersListPost(Body16 body) throws ApiException {
        ApiResponse<InlineResponse20013> resp = merchantCustomersListPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Retrieve list of customers enrolled with Merchant
     * none
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponse20013&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20013> merchantCustomersListPostWithHttpInfo(Body16 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantCustomersListPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20013>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve list of customers enrolled with Merchant (asynchronously)
     * none
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantCustomersListPostAsync(Body16 body, final ApiCallback<InlineResponse20013> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantCustomersListPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20013>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantDeviceRegisterPost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantDeviceRegisterPostCall(Body1 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/device/register";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantDeviceRegisterPostValidateBeforeCall(Body1 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantDeviceRegisterPost(Async)");
        }


        com.squareup.okhttp.Call call = merchantDeviceRegisterPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Register new merchant device
     * Api called when no local data available on registration within device local memory.&lt;br&gt;Enter registration code is displayed to user
     * @param body none (required)
     * @return InlineResponse2001
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse2001 merchantDeviceRegisterPost(Body1 body) throws ApiException {
        ApiResponse<InlineResponse2001> resp = merchantDeviceRegisterPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Register new merchant device
     * Api called when no local data available on registration within device local memory.&lt;br&gt;Enter registration code is displayed to user
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponse2001&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse2001> merchantDeviceRegisterPostWithHttpInfo(Body1 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantDeviceRegisterPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse2001>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Register new merchant device (asynchronously)
     * Api called when no local data available on registration within device local memory.&lt;br&gt;Enter registration code is displayed to user
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantDeviceRegisterPostAsync(Body1 body, final ApiCallback<InlineResponse2001> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantDeviceRegisterPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse2001>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantFetchSchedulednotificationPost
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantFetchSchedulednotificationPostCall(Body26 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/notification/fetchlist";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantFetchSchedulednotificationPostValidateBeforeCall(Body26 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantFetchSchedulednotificationPost(Async)");
        }


        com.squareup.okhttp.Call call = merchantFetchSchedulednotificationPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * fetch list from tbl_notify_req_mst
     * fetch the scheduled notifiacation scheduled message notification table.
     * @param body body (required)
     * @return InlineResponse20019
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20019 merchantFetchSchedulednotificationPost(Body26 body) throws ApiException {
        ApiResponse<InlineResponse20019> resp = merchantFetchSchedulednotificationPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * fetch list from tbl_notify_req_mst
     * fetch the scheduled notifiacation scheduled message notification table.
     * @param body body (required)
     * @return ApiResponse&lt;InlineResponse20019&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20019> merchantFetchSchedulednotificationPostWithHttpInfo(Body26 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantFetchSchedulednotificationPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20019>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * fetch list from tbl_notify_req_mst (asynchronously)
     * fetch the scheduled notifiacation scheduled message notification table.
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantFetchSchedulednotificationPostAsync(Body26 body, final ApiCallback<InlineResponse20019> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantFetchSchedulednotificationPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20019>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantLoginPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantLoginPostCall(Body body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/login";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantLoginPostValidateBeforeCall(Body body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = merchantLoginPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Login API Call.
     * This API call will create Auth token which will be used during each subsequent calls. If token has expired, Login has to be called again in the background to get new token before any other API calls.&lt;br&gt; Response details-&lt;br&gt; monthlypaydueday- On or after this day each month, payment prompt to be displayed. See screen flow for prompt&lt;br&gt;monthlypaydueday is applicable only after pilotenddate or if pilotenddate is NULL. Unil then merchant is on free plan
     * @param body body (optional)
     * @return InlineResponse200
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse200 merchantLoginPost(Body body) throws ApiException {
        ApiResponse<InlineResponse200> resp = merchantLoginPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Login API Call.
     * This API call will create Auth token which will be used during each subsequent calls. If token has expired, Login has to be called again in the background to get new token before any other API calls.&lt;br&gt; Response details-&lt;br&gt; monthlypaydueday- On or after this day each month, payment prompt to be displayed. See screen flow for prompt&lt;br&gt;monthlypaydueday is applicable only after pilotenddate or if pilotenddate is NULL. Unil then merchant is on free plan
     * @param body body (optional)
     * @return ApiResponse&lt;InlineResponse200&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse200> merchantLoginPostWithHttpInfo(Body body) throws ApiException {
        com.squareup.okhttp.Call call = merchantLoginPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse200>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Login API Call. (asynchronously)
     * This API call will create Auth token which will be used during each subsequent calls. If token has expired, Login has to be called again in the background to get new token before any other API calls.&lt;br&gt; Response details-&lt;br&gt; monthlypaydueday- On or after this day each month, payment prompt to be displayed. See screen flow for prompt&lt;br&gt;monthlypaydueday is applicable only after pilotenddate or if pilotenddate is NULL. Unil then merchant is on free plan
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantLoginPostAsync(Body body, final ApiCallback<InlineResponse200> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantLoginPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse200>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantNotificationSendPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantNotificationSendPostCall(Body23 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/notification/send";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantNotificationSendPostValidateBeforeCall(Body23 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = merchantNotificationSendPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Send Notification to customer
     * Send targetted or generic notification
     * @param body body (optional)
     * @return InlineResponse20017
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20017 merchantNotificationSendPost(Body23 body) throws ApiException {
        ApiResponse<InlineResponse20017> resp = merchantNotificationSendPostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Send Notification to customer
     * Send targetted or generic notification
     * @param body body (optional)
     * @return ApiResponse&lt;InlineResponse20017&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20017> merchantNotificationSendPostWithHttpInfo(Body23 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantNotificationSendPostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20017>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Send Notification to customer (asynchronously)
     * Send targetted or generic notification
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantNotificationSendPostAsync(Body23 body, final ApiCallback<InlineResponse20017> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantNotificationSendPostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20017>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantPerformancePost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantPerformancePostCall(Body14 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/performance";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantPerformancePostValidateBeforeCall(Body14 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantPerformancePost(Async)");
        }


        com.squareup.okhttp.Call call = merchantPerformancePostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Retrieve Performance statistics for Merchant
     * none
     * @param body none (required)
     * @return InlineResponse20011
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public InlineResponse20011 merchantPerformancePost(Body14 body) throws ApiException {
        ApiResponse<InlineResponse20011> resp = merchantPerformancePostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Retrieve Performance statistics for Merchant
     * none
     * @param body none (required)
     * @return ApiResponse&lt;InlineResponse20011&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<InlineResponse20011> merchantPerformancePostWithHttpInfo(Body14 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantPerformancePostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<InlineResponse20011>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve Performance statistics for Merchant (asynchronously)
     * none
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantPerformancePostAsync(Body14 body, final ApiCallback<InlineResponse20011> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantPerformancePostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<InlineResponse20011>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for merchantUpdateSchedulednotificationPost
     * @param body body (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantUpdateSchedulednotificationPostCall(Body27 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/notification/update";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantUpdateSchedulednotificationPostValidateBeforeCall(Body27 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = merchantUpdateSchedulednotificationPostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * modify scheduled messages in tbl_notify_req_mst
     * update the scheduled notifiacation scheduled messages in notification table.
     * @param body body (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void merchantUpdateSchedulednotificationPost(Body27 body) throws ApiException {
        merchantUpdateSchedulednotificationPostWithHttpInfo(body);
    }

    /**
     * modify scheduled messages in tbl_notify_req_mst
     * update the scheduled notifiacation scheduled messages in notification table.
     * @param body body (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> merchantUpdateSchedulednotificationPostWithHttpInfo(Body27 body) throws ApiException {
        com.squareup.okhttp.Call call = merchantUpdateSchedulednotificationPostValidateBeforeCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * modify scheduled messages in tbl_notify_req_mst (asynchronously)
     * update the scheduled notifiacation scheduled messages in notification table.
     * @param body body (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantUpdateSchedulednotificationPostAsync(Body27 body, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantUpdateSchedulednotificationPostValidateBeforeCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }

    /**
     * Build call for merchantVoucherSetup
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call merchantVoucherSetupCall(SetUpVoucherList body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/voucher/setup";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call merchantVoucherSetupValidateBeforeCall(SetUpVoucherList body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling merchantVoucherSetup(Async)");
        }


        com.squareup.okhttp.Call call = merchantVoucherSetupCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Onboarding check mobile number API Call.
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return SetUpVoucherResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public SetUpVoucherResponse merchantVoucherSetup(SetUpVoucherList body) throws ApiException {
        ApiResponse<SetUpVoucherResponse> resp = merchantVoucherSetupWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Onboarding check mobile number API Call.
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return ApiResponse&lt;SetUpVoucherResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<SetUpVoucherResponse> merchantVoucherSetupWithHttpInfo(SetUpVoucherList body) throws ApiException {
        com.squareup.okhttp.Call call = merchantVoucherSetupValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<SetUpVoucherResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Onboarding check mobile number API Call. (asynchronously)
     * This api calls when new merchant want to register
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call merchantVoucherSetupAsync(SetUpVoucherList body, final ApiCallback<SetUpVoucherResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = merchantVoucherSetupValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<SetUpVoucherResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for planSucessUpdatePost
     * @param body none (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call planSucessUpdatePostCall(Body30 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/merchant/apicharge/plansuccessupdate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call planSucessUpdatePostValidateBeforeCall(Body30 body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling planSucessUpdatePost(Async)");
        }


        com.squareup.okhttp.Call call = planSucessUpdatePostCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Merchant API to update table after sucess
     * Merchant API to update table after sucess
     * @param body none (required)
     * @return PlanSuccessResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public PlanSuccessResponse planSucessUpdatePost(Body30 body) throws ApiException {
        ApiResponse<PlanSuccessResponse> resp = planSucessUpdatePostWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Merchant API to update table after sucess
     * Merchant API to update table after sucess
     * @param body none (required)
     * @return ApiResponse&lt;PlanSuccessResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<PlanSuccessResponse> planSucessUpdatePostWithHttpInfo(Body30 body) throws ApiException {
        com.squareup.okhttp.Call call = planSucessUpdatePostValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<PlanSuccessResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Merchant API to update table after sucess (asynchronously)
     * Merchant API to update table after sucess
     * @param body none (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call planSucessUpdatePostAsync(Body30 body, final ApiCallback<PlanSuccessResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = planSucessUpdatePostValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<PlanSuccessResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    //Voucher
    /**
     * Build call for genericVoucherGetList
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericVoucherGetListCall(FetchRedeemVoucherListRequestBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/voucher/getlist";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericVoucherGetListValidateBeforeCall(FetchRedeemVoucherListRequestBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericVoucherGetList(Async)");
        }


        com.squareup.okhttp.Call call = genericVoucherGetListCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Onboarding check mobile number API Call.
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return RedeemVoucherListResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public RedeemVoucherListResponse genericVoucherGetList(FetchRedeemVoucherListRequestBody body) throws ApiException {
        ApiResponse<RedeemVoucherListResponse> resp = genericVoucherGetListWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Onboarding check mobile number API Call.
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return ApiResponse&lt;RedeemVoucherListResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<RedeemVoucherListResponse> genericVoucherGetListWithHttpInfo(FetchRedeemVoucherListRequestBody body) throws ApiException {
        com.squareup.okhttp.Call call = genericVoucherGetListValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<RedeemVoucherListResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Onboarding check mobile number API Call. (asynchronously)
     * This api calls when new merchant want to register
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericVoucherGetListAsync(FetchRedeemVoucherListRequestBody body, final ApiCallback<RedeemVoucherListResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericVoucherGetListValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<RedeemVoucherListResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for genericVoucherManage
     * @param body body (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call genericVoucherManageCall(VoucherManageBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/generic/voucher/manage";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call genericVoucherManageValidateBeforeCall(VoucherManageBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling genericVoucherManage(Async)");
        }


        com.squareup.okhttp.Call call = genericVoucherManageCall(body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * API to perform redeem voucher
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return VoucherManageResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public VoucherManageResponse genericVoucherManage(VoucherManageBody body) throws ApiException {
        ApiResponse<VoucherManageResponse> resp = genericVoucherManageWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * API to perform redeem voucher
     * This api calls when new merchant want to register
     * @param body body (required)
     * @return ApiResponse&lt;VoucherManageResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<VoucherManageResponse> genericVoucherManageWithHttpInfo(VoucherManageBody body) throws ApiException {
        com.squareup.okhttp.Call call = genericVoucherManageValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<VoucherManageResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * API to perform redeem voucher (asynchronously)
     * This api calls when new merchant want to register
     * @param body body (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call genericVoucherManageAsync(VoucherManageBody body, final ApiCallback<VoucherManageResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = genericVoucherManageValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<VoucherManageResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
