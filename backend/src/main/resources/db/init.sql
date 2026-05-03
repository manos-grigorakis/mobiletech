-- Categories
INSERT INTO categories (created_at, name, slug)
VALUES (NOW(), 'Smartphones', 'smartphones'),
       (NOW(), 'Refurbished phones', 'refurbished-phones'),
       (NOW(), 'Cases', 'cases'),
       (NOW(), 'Chargers', 'chargers'),
       (NOW(), 'Earbuds', 'earbuds'),
       (NOW(), 'Smartwatches', 'smartwatches'),
       (NOW(), 'Power banks', 'power-banks'),
       (NOW(), 'Screen protectors', 'screen-protectors');

-- Products
INSERT INTO products (created_at, brand, description, image_key, name, price, stock, category_id)
VALUES
    -- ** Smartphones **
    -- Apple
    (NOW(), 'Apple', 'Apple iPhone 15 128GB with A16 Bionic and OLED display',
     '287c11ca-e796-471c-84af-1420e54c2cbd.webp', 'iPhone 15 128GB Black', 829.00, 25, 1),
    (NOW(), 'Apple', 'Apple iPhone 15 256GB premium smartphone with advanced camera',
     'dde166c9-6946-4921-a794-93908680e141.webp', 'iPhone 15 256GB Blue', 949.00, 18, 1),
    (NOW(), 'Apple', 'Apple iPhone 14 128GB with dual camera system',
     '1dc28ded-a8ea-4486-8bb9-4c541d16e051.webp',
     'iPhone 14 128GB Purple', 699.00, 22, 1),
    (NOW(), 'Apple', 'Apple iPhone 13 128GB reliable performance and great battery',
     '717d4095-d55b-427d-b600-d0128cf613b3.webp', 'iPhone 13 128GB Midnight', 599.00, 30, 1),
    (NOW(), 'Apple', 'Apple iPhone SE 2022 64GB compact and powerful',
     'd8f208e0-be6b-4bd8-ba5e-9eb26fd990e8.webp',
     'iPhone SE 2022 64GB Red', 429.00, 20, 1),

    -- Samsung
    (NOW(), 'Samsung', 'Samsung Galaxy S24 128GB flagship with AMOLED display',
     '2dab679e-ebee-4f39-9dd2-3aeea1cecb68.webp', 'Samsung Galaxy S24 128GB Black', 899.00, 15, 1),
    (NOW(), 'Samsung', 'Samsung Galaxy S23 FE 128GB excellent value flagship',
     'ff6afb4e-b86e-4f6a-bbaf-3a990ab40270.webp', 'Samsung Galaxy S23 FE 128GB Green', 599.00, 25, 1),
    (NOW(), 'Samsung', 'Samsung Galaxy A54 128GB midrange performance',
     'a0ce4587-3548-49aa-99c3-524c8a2fd5cc.webp', 'Samsung Galaxy A54 128GB Blue', 399.00, 35, 1),
    (NOW(), 'Samsung', 'Samsung Galaxy A34 128GB budget friendly AMOLED phone',
     'b7b766d1-de5a-409a-b824-61b7e6ac9f9e.webp', 'Samsung Galaxy A34 128GB Black', 329.00, 40, 1),
    (NOW(), 'Samsung', 'Samsung Galaxy Z Flip5 foldable premium device',
     '3a227ff8-7797-455a-a0a7-73fa2d3960bd.webp', 'Samsung Galaxy Z Flip5 256GB Cream', 1099.00, 10, 1),

    -- Xiaomi
    (NOW(), 'Xiaomi', 'Xiaomi 13 256GB flagship with Leica camera',
     '8383025f-eaf3-47b5-8a7b-85493a89c500.webp',
     'Xiaomi 13 256GB Black', 799.00, 18, 1),
    (NOW(), 'Xiaomi', 'Xiaomi 13 Lite 128GB slim and lightweight',
     'b2ea0c8a-5232-4b77-b42b-2cd0a6a3b0d4.webp',
     'Xiaomi 13 Lite 128GB Pink', 499.00, 20, 1),
    (NOW(), 'Xiaomi', 'Redmi Note 13 Pro 256GB great performance and battery',
     '0c5af823-5247-45a6-a1cb-c56900081454.webp', 'Redmi Note 13 Pro 256GB Purple', 349.00, 45, 1),
    (NOW(), 'Xiaomi', 'Redmi Note 12 128GB affordable everyday smartphone',
     '4943f927-c027-4a4f-a3d6-245a165e308e.webp', 'Redmi Note 12 128GB Gray', 249.00, 50, 1),

    -- Realme
    (NOW(), 'Realme', 'Realme GT 5 flagship killer with fast charging',
     'e5a589b2-25dc-4188-a884-e8c735188a8f.webp',
     'Realme GT 5 256GB Black', 549.00, 20, 1),
    (NOW(), 'Realme', 'Realme 11 Pro+ stylish design with strong camera',
     'cd5c1760-dfe7-403c-921c-d1e99ea6db2c.webp', 'Realme 11 Pro+ 256GB Gold', 429.00, 22, 1),
    (NOW(), 'Realme', 'Realme C55 budget device with solid battery', '7fe37b36-fb7f-41a2-839d-06c59d9a2a23.webp',
     'Realme C55 128GB Green', 199.00, 60, 1),

    -- OnePlus
    (NOW(), 'OnePlus', 'OnePlus 12 high-end performance with OxygenOS',
     '19b5f1c2-447f-4372-9e61-e7d0075ab844.webp',
     'OnePlus 12 256GB Black', 899.00, 12, 1),
    (NOW(), 'OnePlus', 'OnePlus Nord 3 balanced performance and price',
     'c0fb1f92-b7f9-438b-98d2-b87146e16727.webp', 'OnePlus Nord 3 128GB Gray', 449.00, 25, 1),

    -- Google
    (NOW(), 'Google', 'Google Pixel 8 with clean Android experience',
     'cfc7f49b-f148-4848-b17a-2451e26c213e.webp',
     'Google Pixel 8 128GB Obsidian', 799.00, 14, 1),
    (NOW(), 'Google', 'Google Pixel 7a compact and powerful', '2972cc28-bef6-42ee-a7e1-864d0e94de30.webp',
     'Google Pixel 7a 128GB White', 509.00, 18, 1),

    -- Huawei
    (NOW(), 'Huawei', 'Huawei P60 Pro excellent camera system', '0f5b59df-c066-496a-a6f2-23723ec9d1d4.webp',
     'Huawei P60 Pro 256GB Black', 999.00, 8, 1),
    (NOW(), 'Huawei', 'Huawei Nova 11 stylish midrange device', 'ee8fe981-b032-4608-a07e-9a0698e4cece.webp',
     'Huawei Nova 11 128GB Green', 429.00, 20, 1),

    -- Motorola
    (NOW(), 'Motorola', 'Motorola Edge 40 premium design and performance',
     '2ae938fb-49b9-4f95-8d99-0bf8b2b58721.webp', 'Motorola Edge 40 256GB Black', 599.00, 16, 1),
    (NOW(), 'Motorola', 'Moto G84 affordable and reliable', 'd624295f-a535-464c-8b45-d50183ecf36d.webp',
     'Moto G84 128GB Blue', 299.00, 35, 1),

    -- Oppo
    (NOW(), 'Oppo', 'Oppo Find X6 flagship with strong camera', '547b8415-1847-4eef-9c4d-f8445428c1f5.webp',
     'Oppo Find X6 256GB Black', 899.00, 10, 1),
    (NOW(), 'Oppo', 'Oppo Reno 10 stylish and balanced device', '69040c57-e84f-43bf-a8dd-31d5f4ff0f37.webp',
     'Oppo Reno 10 256GB Silver', 499.00, 18, 1),

    -- Vivo
    (NOW(), 'Vivo', 'Vivo X90 Pro premium camera focused phone', '039a1f57-3bca-4104-8bbb-0b2ae0efbfcd.webp',
     'Vivo X90 Pro 256GB Black', 899.00, 9, 1),
    (NOW(), 'Vivo', 'Vivo V29 midrange performance and design', '20f30370-be18-4d29-8cf2-2d77a733b9cb.webp',
     'Vivo V29 256GB Blue', 449.00, 17, 1),

    -- ** Refurbished phones **
    -- Apple
    (NOW(), 'Apple', 'Refurbished iPhone 13 128GB Grade A, battery health ≥90%',
     'e9dbe424-2359-4899-b6d0-d1361de4fbe4.webp', 'Refurbished iPhone 13 128GB Black (Grade A)',
     519.00, 12, 2),
    (NOW(), 'Apple', 'Refurbished iPhone 12 128GB Grade A, fully tested',
     '46344256-ef10-40b8-b27f-658f1d3bc913.webp', 'Refurbished iPhone 12 128GB White (Grade A)',
     429.00, 10, 2),
    (NOW(), 'Apple', 'Refurbished iPhone 11 64GB Grade B, minor signs of use',
     '06e5aa81-de2a-4f8d-9ab6-71b970a76683.webp', 'Refurbished iPhone 11 64GB Black (Grade B)', 299.00,
     15, 2),
    (NOW(), 'Apple', 'Refurbished iPhone SE 2020 64GB Grade B, compact device',
     '53d03cc9-566a-4cdd-8e2e-e430c1ee6ec2.webp', 'Refurbished iPhone SE 2020 64GB Red (Grade B)',
     179.00, 20, 2),

    -- Samsung
    (NOW(), 'Samsung', 'Refurbished Galaxy S22 128GB Grade A, excellent condition',
     '8996b50e-9013-4f39-b29c-0f192e8c25cc.webp', 'Refurbished Galaxy S22 128GB Black (Grade A)',
     399.00, 11, 2),
    (NOW(), 'Samsung', 'Refurbished Galaxy S21 FE 128GB Grade A',
     '93756af8-4edb-462a-885d-ff96b9623c0b.webp',
     'Refurbished Galaxy S21 FE 128GB Green (Grade A)', 349.00, 13, 2),
    (NOW(), 'Samsung', 'Refurbished Galaxy S20 128GB Grade B, good condition',
     '91390f5d-5f47-4250-a2cd-50ead068d0c6.webp', 'Refurbished Galaxy S20 128GB Gray (Grade B)',
     269.00, 16, 2),
    (NOW(), 'Samsung', 'Refurbished Galaxy Note 20 256GB Grade B',
     'e8781941-bdcf-4b37-8396-a5c2fac9a2df.webp',
     'Refurbished Galaxy Note 20 256GB Bronze (Grade B)', 329.00, 9, 2),

    -- Xiaomi
    (NOW(), 'Xiaomi', 'Refurbished Xiaomi 12 256GB Grade A, fully functional',
     'fd4b568d-f9fe-4acc-9dc5-c5520c8be0ec.webp', 'Refurbished Xiaomi 12 256GB Blue (Grade A)', 379.00,
     10, 2),
    (NOW(), 'Xiaomi', 'Refurbished Redmi Note 11 Pro 128GB Grade B',
     '04c571aa-4c92-4133-b207-a2366f789505.webp',
     'Refurbished Redmi Note 11 Pro 128GB Black (Grade B)', 199.00, 18, 2),

    -- OnePlus
    (NOW(), 'OnePlus', 'Refurbished OnePlus 9 128GB Grade A, smooth performance',
     '9706eee2-3afb-4d12-bbb3-3b054861f7d2.webp', 'Refurbished OnePlus 9 128GB Black (Grade A)',
     299.00, 8, 2),

    -- Google
    (NOW(), 'Google', 'Refurbished Pixel 6 128GB Grade A, clean Android',
     '876e10e0-6d2a-4e9f-a07a-5034bd3154e1.webp', 'Refurbished Pixel 6 128GB Black (Grade A)', 339.00,
     7, 2),

    -- ** Cases **
    (NOW(), 'Spigen', 'Spigen Rugged Armor case for iPhone 15 with shock absorption',
     '49e7a01d-130d-4423-983d-bad5638f392f.webp', 'Spigen Rugged Armor Case for iPhone 15 Black',
     14.99, 50, 3),
    (NOW(), 'Spigen', 'Spigen Liquid Air slim case for Samsung Galaxy S24',
     '176a3dc3-b1b6-4ed0-83bd-1d23320cae09.webp', 'Spigen Liquid Air Case for Galaxy S24 Black',
     12.99, 45, 3),
    (NOW(), 'ESR', 'ESR Clear Case for iPhone 14 with anti-yellowing material',
     'f4c9333d-a13f-456b-9ec3-b1f7f87d2e22.webp', 'ESR Clear Case for iPhone 14 Transparent', 9.99, 60, 3),
    (NOW(), 'Ringke', 'Ringke Fusion X rugged case for Galaxy S23',
     'c7cf819d-35a0-4a55-a443-60ae3f8ec0ad.webp', 'Ringke Fusion X Case for Galaxy S23 Black', 11.99,
     40, 3),
    (NOW(), 'Nillkin', 'Nillkin CamShield case for Xiaomi 13 with camera cover',
     'b20f76ed-db92-42e2-b379-2e3d891865ed.webp', 'Nillkin CamShield Case for Xiaomi 13 Black', 13.49,
     35, 3),
    (NOW(), 'Baseus', 'Baseus Magnetic Case for iPhone 15 with MagSafe support',
     '6c8405ec-06ea-405c-9dfa-0f07716da72b.webp', 'Baseus Magnetic Case for iPhone 15 Blue', 15.99,
     30, 3),
    (NOW(), 'Spigen', 'Spigen Tough Armor case for iPhone 13 with kickstand',
     '5764213e-e68a-42da-96fd-238b21efb97b.webp', 'Spigen Tough Armor Case for iPhone 13 Black',
     19.99, 25, 3),
    (NOW(), 'ESR', 'ESR Silicone Case for Galaxy A54 soft touch finish',
     'e0b432fa-440f-4d27-8b78-5d1ce4c145ca.webp', 'ESR Silicone Case for Galaxy A54 Black', 10.99,
     55, 3),

    -- ** Chargers **
    (NOW(), 'Anker', 'Anker 511 Nano 20W USB-C charger with Power Delivery fast charging',
     '1ddbc01d-85a9-4187-821c-d2f37174b10b.webp', 'Anker 511 Nano 20W USB-C Charger', 14.99, 60, 4),
    (NOW(), 'Apple', 'Apple 20W USB-C Power Adapter for iPhone fast charging',
     '24e6085e-cccb-4fd5-9ecc-69866b2faddc.webp', 'Apple 20W USB-C Power Adapter', 22.00, 50, 4),
    (NOW(), 'Samsung', 'Samsung 25W Super Fast Charging USB-C charger',
     '388a0300-6c2e-4696-82ba-78a2b805d8d4.webp', 'Samsung 25W Super Fast Charger', 18.90, 45, 4),
    (NOW(), 'Ugreen', 'Ugreen 65W GaN charger with 2x USB-C and 1x USB-A ports',
     '2b1745d5-30f8-46bd-8734-af5ee9da7cc4.webp', 'Ugreen 65W GaN Fast Charger', 39.99, 30, 4),
    (NOW(), 'Baseus', 'Baseus 30W USB-C fast charger compact design',
     '10e2ba27-0f6a-4fe2-9fc0-115c9388f7f4.webp', 'Baseus 30W USB-C Fast Charger', 16.99, 55, 4),
    (NOW(), 'Anker', 'Anker PowerPort III 65W GaN charger with dual USB-C output',
     'd08d6b6a-8269-4401-8b6e-38d285f5ab2e.webp', 'Anker PowerPort III 65W GaN Charger', 42.50, 25, 4),

    -- ** Earbuds **
    -- Apple
    (NOW(), 'Apple', 'Apple AirPods Pro 2 with Active Noise Cancellation and MagSafe case',
     '2f1dc7f0-3352-4582-9024-2b54f2f722a2.webp', 'Apple AirPods Pro 2nd Gen', 279.00, 20, 5),
    (NOW(), 'Apple', 'Apple AirPods 3rd Gen with spatial audio', 'c24e481c-5b2a-4cd4-804a-0de6c66d1e8d.webp',
     'Apple AirPods 3rd Gen', 189.00, 25, 5),

    -- Samsung
    (NOW(), 'Samsung', 'Samsung Galaxy Buds2 Pro with ANC and Hi-Fi sound',
     '88005487-045a-4d52-997e-be9b5277e74b.webp', 'Samsung Galaxy Buds2 Pro Black', 179.00, 22, 5),
    (NOW(), 'Samsung', 'Samsung Galaxy Buds FE affordable ANC earbuds',
     '6d915aef-c81b-4de2-8c83-fe597587ac70.webp', 'Samsung Galaxy Buds FE White', 99.00, 30, 5),

    -- Sony
    (NOW(), 'Sony', 'Sony WF-1000XM5 premium ANC earbuds', '40eaff70-de3b-4017-8ce9-be22419d9305.webp',
     'Sony WF-1000XM5 Black', 299.00, 15, 5),
    (NOW(), 'Sony', 'Sony WF-C700N wireless earbuds with ANC', 'a2de789a-16a1-4d2f-82da-cb0fe3d98735.webp',
     'Sony WF-C700N White', 129.00, 28, 5),

    -- JBL
    (NOW(), 'JBL', 'JBL Tune 230NC with active noise cancelling', 'cdab36bc-c338-4c44-afcc-45989314eed4.webp',
     'JBL Tune 230NC Black', 99.00, 35, 5),
    (NOW(), 'JBL', 'JBL Wave Beam budget true wireless earbuds', '9a4a3f63-0d00-4fdb-978d-57b1b4c9a86f.webp',
     'JBL Wave Beam Blue', 59.00, 40, 5),

    -- Xiaomi
    (NOW(), 'Xiaomi', 'Xiaomi Buds 4 Pro with ANC and wireless charging',
     '568c672f-10aa-4ee3-8f95-8fe342ba47dd.webp', 'Xiaomi Buds 4 Pro Black', 149.00, 26, 5),
    (NOW(), 'Xiaomi', 'Redmi Buds 4 Lite affordable everyday earbuds',
     '3dc3e32b-0279-4d61-a4ca-44b1cb715751.webp', 'Redmi Buds 4 Lite White', 29.90, 60, 5),

    -- Soundcore (Anker)
    (NOW(), 'Soundcore', 'Soundcore Liberty 4 NC with strong ANC',
     '6a8ffb4e-4a2b-4fd8-a168-b035cbf9f184.webp', 'Soundcore Liberty 4 NC Black', 89.00, 32, 5),
    (NOW(), 'Soundcore', 'Soundcore Life P3 balanced sound with ANC',
     '1355f76c-a05d-475c-9a68-b3fb8c4895e1.webp', 'Soundcore Life P3 Blue', 79.00, 34, 5),

    -- Huawei
    (NOW(), 'Huawei', 'Huawei FreeBuds Pro 2 premium ANC earbuds',
     '8038aef9-943f-48e1-8b1e-846990e8a47d.webp', 'Huawei FreeBuds Pro 2 Silver', 179.00, 18, 5),
    (NOW(), 'Huawei', 'Huawei FreeBuds SE budget wireless earbuds',
     'a1431cd1-d3ef-4847-9d5c-9cbefa7586b2.webp', 'Huawei FreeBuds SE White', 49.00, 45, 5),

    -- Realme
    (NOW(), 'Realme', 'Realme Buds Air 5 Pro with ANC', '443205d2-0330-4098-bf10-24d4acc833ad.webp',
     'Realme Buds Air 5 Pro Black', 79.00, 38, 5),
    (NOW(), 'Realme', 'Realme Buds T100 entry level earbuds', '01a52661-eb3f-4d4f-9c3c-5b87b2350250.webp',
     'Realme Buds T100 White', 24.90, 55, 5),

    -- OnePlus
    (NOW(), 'OnePlus', 'OnePlus Buds Pro 2 with spatial audio', '75c236f3-c1c6-4724-8332-bd0b26f146f3.webp',
     'OnePlus Buds Pro 2 Black', 149.00, 20, 5),

    -- Nothing
    (NOW(), 'Nothing', 'Nothing Ear (2) transparent design with ANC', 'e916d6c9-961f-4590-913a-7aad51e032bb.webp',
     'Nothing Ear (2)', 149.00, 22, 5),

    -- ** Smartwatches **
    -- Apple
    (NOW(), 'Apple', 'Apple Watch Series 9 41mm GPS with Retina display and health tracking',
     '269138f2-0a62-4164-8750-22b7676079a0.webp', 'Apple Watch Series 9 41mm Midnight', 429.00, 18,
     6),
    (NOW(), 'Apple', 'Apple Watch SE 2nd Gen 44mm GPS affordable smartwatch',
     '542c8e94-6813-4a17-ae36-2b62f3de2345.webp', 'Apple Watch SE 44mm Starlight', 299.00, 22, 6),

    -- Samsung
    (NOW(), 'Samsung', 'Samsung Galaxy Watch6 44mm with AMOLED display and fitness tracking',
     '06cc26eb-24a9-4394-a735-4631278fe39c.webp', 'Samsung Galaxy Watch6 44mm Black', 319.00, 20, 6),
    (NOW(), 'Samsung', 'Samsung Galaxy Watch5 Pro rugged smartwatch with long battery life',
     '2c536949-463f-4b17-9ed0-367c15faddc0.webp', 'Samsung Galaxy Watch5 Pro Gray', 399.00, 14, 6),

    -- Garmin
    (NOW(), 'Garmin', 'Garmin Venu Sq 2 GPS smartwatch with health monitoring',
     '3d589769-75f7-47b0-bf59-c6a1b814bfc3.webp', 'Garmin Venu Sq 2 Black', 269.00, 16, 6),
    (NOW(), 'Garmin', 'Garmin Forerunner 255 running smartwatch with GPS',
     'd55df665-218f-4c25-9c36-fbf459fafce0.webp', 'Garmin Forerunner 255 Blue', 349.00, 12, 6),

    -- Huawei
    (NOW(), 'Huawei', 'Huawei Watch GT 4 46mm with long battery life',
     '626b809b-a2ed-461c-b789-44d0e4aab577.webp', 'Huawei Watch GT 4 46mm Green', 249.00, 24, 6),

    -- Xiaomi
    (NOW(), 'Xiaomi', 'Xiaomi Watch S1 Active fitness smartwatch',
     '946eb92d-9b04-4e14-a488-215a6236322f.webp', 'Xiaomi Watch S1 Active Black', 199.00, 28, 6),

    -- ** Power Banks **
    (NOW(), 'Anker', 'Anker PowerCore 10000mAh compact power bank with fast charging',
     '4ae063be-8cc7-4c86-b6e5-1da9f618ba59.webp', 'Anker PowerCore 10000mAh Black', 24.99, 40, 7),
    (NOW(), 'Xiaomi', 'Xiaomi Mi Power Bank 3 Pro 20000mAh with 45W fast charging',
     'a8fe8e07-6828-42ec-aaf5-a5a9dff4b7e0.webp', 'Xiaomi Mi Power Bank 3 Pro 20000mAh', 39.99, 30, 7),
    (NOW(), 'Baseus', 'Baseus 10000mAh power bank with USB-C PD and slim design',
     '1c16fc33-9ee0-4ee9-b696-48e37136da3c.webp', 'Baseus 10000mAh Slim Power Bank', 19.99, 50, 7),
    (NOW(), 'Ugreen', 'Ugreen 25000mAh power bank with 65W fast charging for laptops',
     '5a77f2a1-45b3-4dfa-a7e0-bec9ea97a909.webp', 'Ugreen 25000mAh 65W Power Bank', 59.99, 20, 7),

    -- ** Screen protectors **
    -- iPhone
    (NOW(), 'Spigen', 'Spigen Tempered Glass 2-pack for iPhone 15 with 9H hardness',
     '16f0d67e-259f-47f1-88b2-451bb71b8e15.webp', 'Spigen Tempered Glass for iPhone 15 (2-Pack)', 12.99,
     60, 8),

    (NOW(), 'ESR', 'ESR Clear Tempered Glass for iPhone 14 with easy installation kit',
     'f03d4b82-201d-4674-85cc-5d17aaaa456b.webp', 'ESR Tempered Glass for iPhone 14', 9.99, 70, 8),

    (NOW(), 'Nillkin', 'Nillkin Privacy Screen Protector for iPhone 13 anti-spy',
     '20924164-e1ae-4d03-9f9a-fabdd559a2d0.webp', 'Nillkin Privacy Glass for iPhone 13', 14.49, 40, 8),

    (NOW(), 'Baseus', 'Baseus Full Glue Tempered Glass for iPhone 12 edge-to-edge coverage',
     'b8308b47-4293-48df-ab09-41f2e0ff6eff.webp', 'Baseus Full Glue Glass for iPhone 12', 11.99, 50, 8),

    -- Samsung
    (NOW(), 'Spigen', 'Spigen NeoFlex flexible screen protector for Galaxy S24',
     '8f506813-b351-4349-9456-999c141378e6.webp', 'Spigen NeoFlex for Galaxy S24', 13.99, 45, 8),

    (NOW(), 'ESR', 'ESR Tempered Glass for Galaxy S23 with scratch resistance',
     '5352dc59-d5b4-467b-be5e-8b7bac46976e.webp', 'ESR Tempered Glass for Galaxy S23', 9.49, 55, 8),

    (NOW(), 'Nillkin', 'Nillkin Privacy Glass for Galaxy S22 anti-spy protection',
     'f226b8e6-09d2-4759-b106-581b3877a202.webp', 'Nillkin Privacy Glass for Galaxy S22', 13.99, 38, 8),

    (NOW(), 'Baseus', 'Baseus Tempered Glass for Galaxy A54 durable protection',
     '66fb0847-98f3-4906-8a04-bc4d4987f887.webp', 'Baseus Tempered Glass for Galaxy A54', 8.99, 65, 8),

    -- Xiaomi
    (NOW(), 'Nillkin', 'Nillkin Tempered Glass for Xiaomi 13 with oleophobic coating',
     '53f87b53-c584-48e7-8b9e-1b0e1ee06a76.webp', 'Nillkin Tempered Glass for Xiaomi 13', 10.49, 42, 8),

    (NOW(), 'Baseus', 'Baseus Privacy Screen Protector for Redmi Note 12',
     '1d91bce8-b351-4772-be28-25c45b9060e3.webp', 'Baseus Privacy Glass for Redmi Note 12', 9.99, 48, 8),

    -- Universal / extra
    (NOW(), 'Spigen', 'Spigen EZ Fit Tempered Glass with installation frame for iPhone 15 Pro',
     'ed5431a3-79e9-44c7-b60a-b95a14603be6.webp', 'Spigen EZ Fit Glass for iPhone 15 Pro', 15.99, 35, 8),

    (NOW(), 'ESR', 'ESR 3-pack tempered glass universal fit for 6.5 inch displays',
     '72d047a7-ef79-46e4-9946-4cd0d4325dfe.webp', 'ESR Universal Tempered Glass 6.5"', 7.99, 80, 8);