SELECT PAYLOAD_TYPE, AGGREGATE_IDENTIFIER, UTF8TOSTRING(PAYLOAD) FROM DOMAIN_EVENT_ENTRY ORDER BY SEQUENCE_NUMBER;