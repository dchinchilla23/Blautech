CREATE TABLE IF NOT EXISTS public.clientes (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  email VARCHAR(100),
  fecha_registro TIMESTAMP DEFAULT NOW()
);

INSERT INTO public.clientes (nombre, apellido, email)
VALUES 
  ('Laura', 'Ramírez', 'laura.ramirez@example.com'),
  ('Carlos', 'Soto', 'carlos.soto@example.com'),
  ('Ana', 'González', 'ana.gonzalez@example.com');
