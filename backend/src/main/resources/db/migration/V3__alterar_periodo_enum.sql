ALTER TABLE api_publica ADD CONSTRAINT valor_frequencia CHECK (api_frequencia IN ('diariamente', 'semanalmente', 'mensalmente'));
ALTER TABLE portal ADD CONSTRAINT valor_frequencia CHECK (por_frequencia IN ('diariamente', 'semanalmente', 'mensalmente'));
ALTER TABLE result_api ADD UNIQUE(api_id, res_data);