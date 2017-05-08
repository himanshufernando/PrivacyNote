package tkhub.project.PrivacyNote.data.database;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Himanshu on 3/16/2017.
 */

public class Migration implements RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        System.out.println("oldVersion :"+oldVersion);
        System.out.println("newVersion :"+newVersion);

        if (oldVersion == 0) {
            oldVersion++;
        }

        if(oldVersion == 1){

            schema.create("ChoiceDB")
                    .addField("id", int.class);

            oldVersion++;
        }

        if(oldVersion == 2){

            RealmObjectSchema personSchema = schema.get("ChoiceDB");
            personSchema
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("id",0);
                        }
                    });
            oldVersion++;
        }


    }
}
