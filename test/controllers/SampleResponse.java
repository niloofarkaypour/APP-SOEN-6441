package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleResponse {

	final static String QUERY_STRING_OPTION0 = "Concordia University";
	final static String QUERY_STRING_OPTION2 = "Mayank Vadariya";
	final static String QUERY_STRING_OPTION4 = "Yasaman Sarlati";

	final static Object RESPONSE1 = "{\r\n" + "  \"statuses\": [\r\n" + "    {\r\n"
			+ "      \"created_at\": \"Tue Nov 10 20:30:02 +0000 2020\",\r\n" + "      \"id\": 1326260850177486800,\r\n"
			+ "      \"id_str\": \"1326260850177486849\",\r\n"
			+ "      \"full_text\": \"RT @qjack10: I am extremely excited to announce I have received my fourth offer from Concordia University Wisconsin to continue my academic…\",\r\n"
			+ "      \"truncated\": false,\r\n" + "      \"display_text_range\": [\r\n" + "        0,\r\n"
			+ "        140\r\n" + "      ],\r\n" + "      \"entities\": {\r\n" + "        \"hashtags\": [],\r\n"
			+ "        \"symbols\": [],\r\n" + "        \"user_mentions\": [\r\n" + "          {\r\n"
			+ "            \"screen_name\": \"qjack10\",\r\n" + "            \"name\": \"Quinton Johns\",\r\n"
			+ "            \"id\": 1046166560,\r\n" + "            \"id_str\": \"1046166560\",\r\n"
			+ "            \"indices\": [\r\n" + "              3,\r\n" + "              11\r\n" + "            ]\r\n"
			+ "          }\r\n" + "        ],\r\n" + "        \"urls\": []\r\n" + "      },\r\n"
			+ "      \"metadata\": {\r\n" + "        \"iso_language_code\": \"en\",\r\n"
			+ "        \"result_type\": \"recent\"\r\n" + "      },\r\n"
			+ "      \"source\": \"<a href=\\\"http://twitter.com/download/iphone\\\" rel=\\\"nofollow\\\">Twitter for iPhone</a>\",\r\n"
			+ "      \"in_reply_to_status_id\": null,\r\n" + "      \"in_reply_to_status_id_str\": null,\r\n"
			+ "      \"in_reply_to_user_id\": null,\r\n" + "      \"in_reply_to_user_id_str\": null,\r\n"
			+ "      \"in_reply_to_screen_name\": null,\r\n" + "      \"user\": {\r\n"
			+ "        \"id\": 1285240153561276400,\r\n" + "        \"id_str\": \"1285240153561276418\",\r\n"
			+ "        \"name\": \"Prep Redzone Kansas\",\r\n" + "        \"screen_name\": \"PrepRedzoneKS\",\r\n"
			+ "        \"location\": \"Kansas, USA\",\r\n"
			+ "        \"description\": \"The comprehensive authority for High School football prospect coverage and analysis in Kansas | Member of the @PrepRedzone network |\",\r\n"
			+ "        \"url\": \"https://t.co/jp9L9aUGOK\",\r\n" + "        \"entities\": {\r\n"
			+ "          \"url\": {\r\n" + "            \"urls\": [\r\n" + "              {\r\n"
			+ "                \"url\": \"https://t.co/jp9L9aUGOK\",\r\n"
			+ "                \"expanded_url\": \"http://prepredzone.com/kansas\",\r\n"
			+ "                \"display_url\": \"prepredzone.com/kansas\",\r\n" + "                \"indices\": [\r\n"
			+ "                  0,\r\n" + "                  23\r\n" + "                ]\r\n" + "              }\r\n"
			+ "            ]\r\n" + "          },\r\n" + "          \"description\": {\r\n"
			+ "            \"urls\": []\r\n" + "          }\r\n" + "        },\r\n"
			+ "        \"protected\": false,\r\n" + "        \"followers_count\": 962,\r\n"
			+ "        \"friends_count\": 950,\r\n" + "        \"listed_count\": 2,\r\n"
			+ "        \"created_at\": \"Mon Jul 20 15:48:31 +0000 2020\",\r\n"
			+ "        \"favourites_count\": 7103,\r\n" + "        \"utc_offset\": null,\r\n"
			+ "        \"time_zone\": null,\r\n" + "        \"geo_enabled\": false,\r\n"
			+ "        \"verified\": false,\r\n" + "        \"statuses_count\": 4621,\r\n"
			+ "        \"lang\": null,\r\n" + "        \"contributors_enabled\": false,\r\n"
			+ "        \"is_translator\": false,\r\n" + "        \"is_translation_enabled\": false,\r\n"
			+ "        \"profile_background_color\": \"F5F8FA\",\r\n"
			+ "        \"profile_background_image_url\": null,\r\n"
			+ "        \"profile_background_image_url_https\": null,\r\n"
			+ "        \"profile_background_tile\": false,\r\n"
			+ "        \"profile_image_url\": \"http://pbs.twimg.com/profile_images/1285240224767979521/0_R5J9gf_normal.jpg\",\r\n"
			+ "        \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1285240224767979521/0_R5J9gf_normal.jpg\",\r\n"
			+ "        \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/1285240153561276418/1595260185\",\r\n"
			+ "        \"profile_link_color\": \"1DA1F2\",\r\n"
			+ "        \"profile_sidebar_border_color\": \"C0DEED\",\r\n"
			+ "        \"profile_sidebar_fill_color\": \"DDEEF6\",\r\n"
			+ "        \"profile_text_color\": \"333333\",\r\n" + "        \"profile_use_background_image\": true,\r\n"
			+ "        \"has_extended_profile\": true,\r\n" + "        \"default_profile\": true,\r\n"
			+ "        \"default_profile_image\": false,\r\n" + "        \"following\": null,\r\n"
			+ "        \"follow_request_sent\": null,\r\n" + "        \"notifications\": null,\r\n"
			+ "        \"translator_type\": \"none\"\r\n" + "      },\r\n" + "      \"geo\": null,\r\n"
			+ "      \"coordinates\": null,\r\n" + "      \"place\": null,\r\n" + "      \"contributors\": null,\r\n"
			+ "      \"retweeted_status\": {\r\n" + "        \"created_at\": \"Mon Nov 09 23:40:53 +0000 2020\",\r\n"
			+ "        \"id\": 1325946495070244900,\r\n" + "        \"id_str\": \"1325946495070244864\",\r\n"
			+ "        \"full_text\": \"I am extremely excited to announce I have received my fourth offer from Concordia University Wisconsin to continue my academic and athletic career!\\n@walkercuw @CUWFB @CoachDavidBowen \\n\\n#CUDub #PRIDE https://t.co/a5K8kDkb8q\",\r\n"
			+ "        \"truncated\": false,\r\n" + "        \"display_text_range\": [\r\n" + "          0,\r\n"
			+ "          198\r\n" + "        ],\r\n" + "        \"entities\": {\r\n" + "          \"hashtags\": [\r\n"
			+ "            {\r\n" + "              \"text\": \"CUDub\",\r\n" + "              \"indices\": [\r\n"
			+ "                185,\r\n" + "                191\r\n" + "              ]\r\n" + "            },\r\n"
			+ "            {\r\n" + "              \"text\": \"PRIDE\",\r\n" + "              \"indices\": [\r\n"
			+ "                192,\r\n" + "                198\r\n" + "              ]\r\n" + "            }\r\n"
			+ "          ],\r\n" + "          \"symbols\": [],\r\n" + "          \"user_mentions\": [\r\n"
			+ "            {\r\n" + "              \"screen_name\": \"walkercuw\",\r\n"
			+ "              \"name\": \"Jeff Walker\",\r\n" + "              \"id\": 2640652617,\r\n"
			+ "              \"id_str\": \"2640652617\",\r\n" + "              \"indices\": [\r\n"
			+ "                148,\r\n" + "                158\r\n" + "              ]\r\n" + "            },\r\n"
			+ "            {\r\n" + "              \"screen_name\": \"CUWFB\",\r\n"
			+ "              \"name\": \"CUW Falcon Football\",\r\n" + "              \"id\": 1720818216,\r\n"
			+ "              \"id_str\": \"1720818216\",\r\n" + "              \"indices\": [\r\n"
			+ "                159,\r\n" + "                165\r\n" + "              ]\r\n" + "            },\r\n"
			+ "            {\r\n" + "              \"screen_name\": \"CoachDavidBowen\",\r\n"
			+ "              \"name\": \"Baldwin Football\",\r\n" + "              \"id\": 1139265282445381600,\r\n"
			+ "              \"id_str\": \"1139265282445381635\",\r\n" + "              \"indices\": [\r\n"
			+ "                166,\r\n" + "                182\r\n" + "              ]\r\n" + "            }\r\n"
			+ "          ],\r\n" + "          \"urls\": [],\r\n" + "          \"media\": [\r\n" + "            {\r\n"
			+ "              \"id\": 1325946475461943300,\r\n"
			+ "              \"id_str\": \"1325946475461943302\",\r\n" + "              \"indices\": [\r\n"
			+ "                199,\r\n" + "                222\r\n" + "              ],\r\n"
			+ "              \"media_url\": \"http://pbs.twimg.com/media/Ema1S1UXIAYk_i-.jpg\",\r\n"
			+ "              \"media_url_https\": \"https://pbs.twimg.com/media/Ema1S1UXIAYk_i-.jpg\",\r\n"
			+ "              \"url\": \"https://t.co/a5K8kDkb8q\",\r\n"
			+ "              \"display_url\": \"pic.twitter.com/a5K8kDkb8q\",\r\n"
			+ "              \"expanded_url\": \"https://twitter.com/qjack10/status/1325946495070244864/photo/1\",\r\n"
			+ "              \"type\": \"photo\",\r\n" + "              \"sizes\": {\r\n"
			+ "                \"medium\": {\r\n" + "                  \"w\": 827,\r\n"
			+ "                  \"h\": 825,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                },\r\n" + "                \"thumb\": {\r\n" + "                  \"w\": 150,\r\n"
			+ "                  \"h\": 150,\r\n" + "                  \"resize\": \"crop\"\r\n"
			+ "                },\r\n" + "                \"large\": {\r\n" + "                  \"w\": 827,\r\n"
			+ "                  \"h\": 825,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                },\r\n" + "                \"small\": {\r\n" + "                  \"w\": 680,\r\n"
			+ "                  \"h\": 678,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          ]\r\n"
			+ "        },\r\n" + "        \"extended_entities\": {\r\n" + "          \"media\": [\r\n"
			+ "            {\r\n" + "              \"id\": 1325946475461943300,\r\n"
			+ "              \"id_str\": \"1325946475461943302\",\r\n" + "              \"indices\": [\r\n"
			+ "                199,\r\n" + "                222\r\n" + "              ],\r\n"
			+ "              \"media_url\": \"http://pbs.twimg.com/media/Ema1S1UXIAYk_i-.jpg\",\r\n"
			+ "              \"media_url_https\": \"https://pbs.twimg.com/media/Ema1S1UXIAYk_i-.jpg\",\r\n"
			+ "              \"url\": \"https://t.co/a5K8kDkb8q\",\r\n"
			+ "              \"display_url\": \"pic.twitter.com/a5K8kDkb8q\",\r\n"
			+ "              \"expanded_url\": \"https://twitter.com/qjack10/status/1325946495070244864/photo/1\",\r\n"
			+ "              \"type\": \"photo\",\r\n" + "              \"sizes\": {\r\n"
			+ "                \"medium\": {\r\n" + "                  \"w\": 827,\r\n"
			+ "                  \"h\": 825,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                },\r\n" + "                \"thumb\": {\r\n" + "                  \"w\": 150,\r\n"
			+ "                  \"h\": 150,\r\n" + "                  \"resize\": \"crop\"\r\n"
			+ "                },\r\n" + "                \"large\": {\r\n" + "                  \"w\": 827,\r\n"
			+ "                  \"h\": 825,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                },\r\n" + "                \"small\": {\r\n" + "                  \"w\": 680,\r\n"
			+ "                  \"h\": 678,\r\n" + "                  \"resize\": \"fit\"\r\n"
			+ "                }\r\n" + "              }\r\n" + "            }\r\n" + "          ]\r\n"
			+ "        },\r\n" + "        \"metadata\": {\r\n" + "          \"iso_language_code\": \"en\",\r\n"
			+ "          \"result_type\": \"recent\"\r\n" + "        },\r\n"
			+ "        \"source\": \"<a href=\\\"http://twitter.com/download/iphone\\\" rel=\\\"nofollow\\\">Twitter for iPhone</a>\",\r\n"
			+ "        \"in_reply_to_status_id\": null,\r\n" + "        \"in_reply_to_status_id_str\": null,\r\n"
			+ "        \"in_reply_to_user_id\": null,\r\n" + "        \"in_reply_to_user_id_str\": null,\r\n"
			+ "        \"in_reply_to_screen_name\": null,\r\n" + "        \"user\": {\r\n"
			+ "          \"id\": 1046166560,\r\n" + "          \"id_str\": \"1046166560\",\r\n"
			+ "          \"name\": \"Quinton Johns\",\r\n" + "          \"screen_name\": \"qjack10\",\r\n"
			+ "          \"location\": \"Gardner, Kansas\",\r\n"
			+ "          \"description\": \"John 11-35/2021 BHS QB/5’11 175/3.26 GPA/ https://t.co/zI1I7KoLYP\",\r\n"
			+ "          \"url\": null,\r\n" + "          \"entities\": {\r\n" + "            \"description\": {\r\n"
			+ "              \"urls\": [\r\n" + "                {\r\n"
			+ "                  \"url\": \"https://t.co/zI1I7KoLYP\",\r\n"
			+ "                  \"expanded_url\": \"http://www.hudl.com/profile/14950434\",\r\n"
			+ "                  \"display_url\": \"hudl.com/profile/149504…\",\r\n"
			+ "                  \"indices\": [\r\n" + "                    42,\r\n" + "                    65\r\n"
			+ "                  ]\r\n" + "                }\r\n" + "              ]\r\n" + "            }\r\n"
			+ "          },\r\n" + "          \"protected\": false,\r\n" + "          \"followers_count\": 299,\r\n"
			+ "          \"friends_count\": 295,\r\n" + "          \"listed_count\": 0,\r\n"
			+ "          \"created_at\": \"Sat Dec 29 22:34:33 +0000 2012\",\r\n"
			+ "          \"favourites_count\": 914,\r\n" + "          \"utc_offset\": null,\r\n"
			+ "          \"time_zone\": null,\r\n" + "          \"geo_enabled\": false,\r\n"
			+ "          \"verified\": false,\r\n" + "          \"statuses_count\": 177,\r\n"
			+ "          \"lang\": null,\r\n" + "          \"contributors_enabled\": false,\r\n"
			+ "          \"is_translator\": false,\r\n" + "          \"is_translation_enabled\": false,\r\n"
			+ "          \"profile_background_color\": \"C0DEED\",\r\n"
			+ "          \"profile_background_image_url\": \"http://abs.twimg.com/images/themes/theme1/bg.png\",\r\n"
			+ "          \"profile_background_image_url_https\": \"https://abs.twimg.com/images/themes/theme1/bg.png\",\r\n"
			+ "          \"profile_background_tile\": false,\r\n"
			+ "          \"profile_image_url\": \"http://pbs.twimg.com/profile_images/1323422125429002240/B5aW6Eut_normal.jpg\",\r\n"
			+ "          \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1323422125429002240/B5aW6Eut_normal.jpg\",\r\n"
			+ "          \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/1046166560/1603062568\",\r\n"
			+ "          \"profile_link_color\": \"1DA1F2\",\r\n"
			+ "          \"profile_sidebar_border_color\": \"C0DEED\",\r\n"
			+ "          \"profile_sidebar_fill_color\": \"DDEEF6\",\r\n"
			+ "          \"profile_text_color\": \"333333\",\r\n"
			+ "          \"profile_use_background_image\": true,\r\n" + "          \"has_extended_profile\": false,\r\n"
			+ "          \"default_profile\": true,\r\n" + "          \"default_profile_image\": false,\r\n"
			+ "          \"following\": null,\r\n" + "          \"follow_request_sent\": null,\r\n"
			+ "          \"notifications\": null,\r\n" + "          \"translator_type\": \"none\"\r\n"
			+ "        },\r\n" + "        \"geo\": null,\r\n" + "        \"coordinates\": null,\r\n"
			+ "        \"place\": null,\r\n" + "        \"contributors\": null,\r\n"
			+ "        \"is_quote_status\": false,\r\n" + "        \"retweet_count\": 5,\r\n"
			+ "        \"favorite_count\": 27,\r\n" + "        \"favorited\": false,\r\n"
			+ "        \"retweeted\": false,\r\n" + "        \"possibly_sensitive\": false,\r\n"
			+ "        \"lang\": \"en\"\r\n" + "      },\r\n" + "      \"is_quote_status\": false,\r\n"
			+ "      \"retweet_count\": 5,\r\n" + "      \"favorite_count\": 0,\r\n" + "      \"favorited\": false,\r\n"
			+ "      \"retweeted\": false,\r\n" + "      \"lang\": \"en\"\r\n" + "    }\r\n" + "  ],\r\n"
			+ "  \"search_metadata\": {\r\n" + "    \"completed_in\": 0.074,\r\n"
			+ "    \"max_id\": 1326260850177486800,\r\n" + "    \"max_id_str\": \"1326260850177486849\",\r\n"
			+ "    \"next_results\": \"?max_id=1326221433761042435&q=%22Concordia%20University%22&count=10&include_entities=1\",\r\n"
			+ "    \"query\": \"%22Concordia+University%22\",\r\n"
			+ "    \"refresh_url\": \"?since_id=1326260850177486849&q=%22Concordia%20University%22&include_entities=1\",\r\n"
			+ "    \"count\": 10,\r\n" + "    \"since_id\": 0,\r\n" + "    \"since_id_str\": \"0\"\r\n" + "  }\r\n"
			+ "}";

	public static JsonNode returnJsonNodeResponse(int responseCategory) {
		String response2 = "";
		FileInputStream fis;

		if (responseCategory == 0) {
			try {
				fis = new FileInputStream("EmptyResponse.txt");
				response2 = IOUtils.toString(fis, "UTF-8");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} else if (responseCategory == 1) {
			try {
				fis = new FileInputStream("ResponseWith10Results.txt");
				response2 = IOUtils.toString(fis, "UTF-8");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readValue(response2, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonNode;
	}
}
