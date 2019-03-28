#!/usr/bin/env bash

CORE=$1

# <dynamicField name="*_sni" type="string" indexed="false" stored="true"/>

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-dynamic-field":{
     "name":"*_sni",
     "type":"string",
     "stored":true,
     "indexed":false
  }
}' http://localhost:8983/api/cores/${CORE}/schema

# <copyField source="*_ss" dest="_text_"/>
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-copy-field":{
     "source":"*_ss",
     "dest":"_text_"
  }
}' http://localhost:8983/api/cores/${CORE}/schema
