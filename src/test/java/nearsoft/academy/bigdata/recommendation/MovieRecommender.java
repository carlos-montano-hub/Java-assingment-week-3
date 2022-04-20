package nearsoft.academy.bigdata.recommendation;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRecommender {
    private final DataModel model;
    Builder2 construct;
   public MovieRecommender(String pathTxt) throws IOException {
       this.construct = new Builder2(pathTxt);
       this.model= new FileDataModel(construct.numericCSVFile);
   }



    public int getTotalReviews() {
        System.out.println("reviews: " + construct.scoreCount);
        return construct.scoreCount;
    }

    public int getTotalProducts() {
        System.out.println("products: " + construct.productList.size());
        return construct.productList.size();
    }

    public int getTotalUsers() {
        System.out.println("users: " + construct.userDictionary.size());
        return construct.userDictionary.size();
    }

    List<String> getRecommendationsForUser(String alphaUserID) throws TasteException {
        List<String> rList = new ArrayList<>();
        UserSimilarity similarity = new PearsonCorrelationSimilarity(this.model);
        //System.out.println("similarity");
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, this.model);
        //System.out.println("neighborhood");
        UserBasedRecommender recommender = new GenericUserBasedRecommender(this.model, neighborhood, similarity);
        //System.out.println("recommender");
        //System.out.println(construct.userDictionary.get(alphaUserID));
        long name = construct.userDictionary.get(alphaUserID);

        List<RecommendedItem> recommendations = recommender.recommend(name, 3); //esta linea regresa una lista null[]
        //System.out.println(recommendations);
        System.out.println("recommendations for user " + alphaUserID + ":");
        for (RecommendedItem recommendation : recommendations) {
            rList.add(construct.productList.get((int)recommendation.getItemID()));
            System.out.println(construct.productList.get((int)recommendation.getItemID()) + ": " + recommendation.getValue());

        }

        //System.out.println(rList);
        return rList;
    }
}
