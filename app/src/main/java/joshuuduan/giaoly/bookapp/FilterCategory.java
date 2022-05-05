package joshuuduan.giaoly.bookapp;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterCategory extends Filter {



    //arrayList in which we want to search
    ArrayList<ModelCategory> filterList;

    //adapter in which filter need to be implemented
    AdapterCategory adapterCategory;

    //constructor
    public FilterCategory(ArrayList<ModelCategory> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        //value should not be null and empty
        if(charSequence != null && charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ModelCategory> filterModel = new ArrayList<>();
            for (int i=0; i < filterList.size() ;i++){
                //validate
                if(filterList.get(i).getCategory().toUpperCase().contains(charSequence)){
                    //add to tiltered list
                    filterModel.add(filterList.get(i));
                }
            }
            results.count = filterModel.size();
            results.values = filterModel;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        //apply filter changes
         adapterCategory.categoryArrayList = (ArrayList<ModelCategory>)filterResults.values;

         //notify changes
        adapterCategory.notifyDataSetChanged();
    }
}
