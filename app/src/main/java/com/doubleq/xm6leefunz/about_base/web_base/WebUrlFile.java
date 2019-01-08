package com.doubleq.xm6leefunz.about_base.web_base;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doubleq.xm6leefunz.about_utils.MD5Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class WebUrlFile {
   static String base="data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/wAALCAGuAa4BAREA/8QAHQABAQEAAwEBAQEAAAAAAAAAAAgHAQYJBQIEA//EAF4QAAABCAQJCQQGBQoDBQgDAAABAgMFBhESEwQHCBQVFhghIjE3hLQkMkFGVmakxNMXYaXjIyUnM0RiOEVxk9I2UVRVkZKUsrPRc3SBNDVDsfAoQlJTY2SVwXKhov/aAAgBAQAAPwCqQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAfIaloFYyyipK5X1Kuito0M1NLOSQxHEMN0TSFKXSOITMTpHQcoKrHtN4ClemGUFVj2m8BSvTDKCqx7TeApXphlBVY9pvAUr0wygqse03gKV6YZQVWPabwFK9MMoKrHtN4ClemGUFVj2m8BSvTHamFrBZhu79ios7/coJ/J0qKCOKHnmmvfAdqfqHawAZ81NcLCsqvaSpl8vLqsqNBNQ3ROfDEaQ8mkaYUhdE4hcxekfLygqse03gKV6YZQVWPabwFK9MMoKrHtN4ClemGUFVj2m8BSvTHfmWaBWNSoaKuVDSr0raTFKTSzjIoTimF0TiEKTSNKTOQfMbmsFmGEuWNSzuF9jkcnSpY4IYuYac50Zut2sdVygqse03gKV6Y4ygqsO03gKV6YZQVWHabwFK9Md/ZdoFY1Kioy4UNJvStpMUpNLORxQnFMO0TiEKTSNKTOQfKbmsFmGEuWNSzuF9jkcnSpY4IYuYac50Zut2sfJZauJhWpXtGU6hXl6WVJjlIbonMihNOPO0jjCEI400pc5egaCADqrc1gswwlyxqWdwvscjk6VLHBDFzDTnOjN1u1j5DLVxMK1K9oynUK8vSypMcpDdE5kUJpx52kcYQhHGmlLnL0DQRlWUHVh2m8BSvTDKDqw7TeApXpjtbC1gsw3d+xUWd/uUE/k6VFBHFDzzTXvgO1P1DtQAM+amuFhWVXtJUy+Xl1WVGgmobonPhiNIeTSNMKQuicQuYvSPl5QVWPabwFK9MMoKrHtN4ClemGUFVj2m8BSvTDKCqx7TeApXphlBVY9pvAUr0wygqse03gKV6YZQVWPabwFK9MMoKrHtN4ClemNVAAAAGVWo9hTTbrxSIQAAAAAAqqwx123HzAqoAEAWo9urTbrwqIZUAAL/subCWZ3rikoyq3P1J37y4lUAF/2XNhLM71xSUZVbn6k795cZVZc27MzvXCpRf4AJVtz9Sd+8uMqsubdmZ3rhUov8eVYCqrDHXbcfMCqgAQBaj26tNuvCohlQAAAAD1UAAAAGVWo9hLTbrxSIQAPVQAAAEAWo9uzTbrwqIarYY67bj5garaj2EtNuvFIhAA9VBAFqPbq0268KiGVDVbLu3Vmd64VKL/AAASrbn6k795cSqA9VAAZVaj2EtNu3FIhAA9VBAFqPbs0268KiGq2Geu24+YGq2othLTbrxSIQAPVQAGVWo9hLTbrxSIQAPVQQBaj26tNuvCohqthjrtuPmBVQAADKrUewlpt14pEIAHqoAAAAMqtR7CWm3XikQgAeqgkCvmuFumVrXXinUK8uito0iUhuiA+GJAjPLpHGFKXSOKXOXpHQMoKs7tN4Ci+mGUFWd2m8BRfTDKCrO7TeAovpi/hAFqPbs0268KiGq2GOu24+YGq2o9hLTbrxSIQANVyg6z+03gKL6Y3+q6r5mK02FVjZN4rMKtIspt7pl4SoJktKeiM0ERxphHGIzCZjSPc8ucpSjK7VlXzMMJiviqrLhfb1P5QlSxwSYeecc50Z2p2sdVsuk+3Vmd64VKL+EAZQdZ/abwFF9MV9UM0CzamqhRrhe0q9rGkz5qaWajihTpDDdE0hCE0TSEzE6Bi1ubqTv3lxKoD1UEgV81wt0yta68U6hXl0VtGkSkN0QHwxIEZ5dI4wpS6RxS5y9I6BlBVndpvAUX0x8pqa4W6alRUpTL5eXpXUmGahuaAyKE4hxNI0whSZzSFzF6Bnw9VBnzU1PMK1K9pK5XqivSypMM1Ne05kUJpDCaJp5CE0TSEzE6B9ZhqvmYYS+4qqy4X2C8coSpY4Ioeecc50Z2p2sfValn1Y1KjpKmXtFvatpMM1DMORxQnEOJpGlIUmkaQuYvQOgEs+1Y9mfH0r1BKxLQVZ3abwFF9MV7UM0KzamqhRrle0q9rKkz5qaWajihTpDDdE0hCE0TSEzE6BoJBlVqPYS0268UiEAD1UEA2o9uzTbrwqIarYZ67bl5gbRXy0KzZaqheLlRUq6LKjSJSaWafDEnRmF0TiFIXROKTOTpEg5QdZ3abwFF9MMoOs7tN4Ci+mOcoOs7tN4Ci+mN/spVgtO3eNGNSzv9yusjk6JFBHOi5hpr3wG636h2q1HsJabdeKRCAB6qAAAADKrUewlpt14pEIAHqoIBtR7dmm3bhUQykAHqoIAtR7dmm3XhUQ1Wwx123HzA1W1HsJabdeKRCAAF/wBlzYSzO9cUlCvOqL2p4E+u8FYNn/hJ8yZL/Oa50v3vf7hlZKo/YV9o+G8O4F/V10us6d9B97GfC6bFzSvhdme8nGVz3J+LfJEqi/7Lmwlmd64pKMqtzE/kTv3lxgNVrI49t0rGcvtwvs3lEqbBAiPSc2I174Hayaxv+SP32+E/ODK47k/Fvkjj2Se3UntGw3gLDX6vul6kyfoPvYzInyouaR0Ts7nlyuvKqP2WYE+u8K4Sn/hJEuXL/Oc98z3Od7x1Wq5kce26VjOX24X2byiVNggRHpObEa98DtZNY37JG77fCfnBlc9yfi3yRv8AVc12PbCqxo7lcL7N5PNmwQJT0fOhNe+B+omsdUrzrc9lmBPqTCuEp/4uRLly/wAhz3zPc53vHVarrRWPbdKxnMV7hfZvKMITYIER6TmyjXvgdrJrFAEErZI/fb4T84Pa57Cvs5wJh3Av6wvd1nTvp/uoD4XTYecV8L8z3E1Soutz2p4b+pMFYNkfi58yZM/Ia50v3vf7gtR7CWm3XikQgAeqggG1Ht2abdeFRDVbDPXbcfMDVLUmwlpt14pEIAABVdhnrtuPmBqlqPYS0268UiEAD1UAAAAGVWo9hLTbrxSIQAPVQQDaj27NNu3CohlIAPVQQBaj27NNuvCohqthjrtuPmBqtqPYS0268UiEADVcnys/sz4+i+oK+qFZ9ZsvVQo1OvaNdVlRp81DMNPhiTpDyaRpSkK804hcxR9duawWYYS5Y1LO4X2ORydKljghi5hpznRm63axlVaNYDMVpsKs2NYNZ4VaRZSrrQ7ulQTJaUxKfppTTTCOMRnlznEe5xM5SELgGT7Wd2Z8fRfUGUiv6hq4WFZWqhRqZfLy6rKjT5qG6Jz4Yk6Q8mkaYUhdE4hcxR0G1ZWCzDd4r4qrO/3K9T+TpUUEcmHnmmvfAdqfqHVLLu3Vmd64VKL+EA5PtZ/Znx9F9QV7UKz6zZaqhRqZfUW6LKjT5qGYafDEnSHk0jSlIXROIXMUYtbl6k775cYtUK0KsZatdRrhe0q6K2jT5qaWcfDEgSGE0TSFKXSOITMTpFf5QVWPabwFK9MSpk+Vn9mfH0X1BX1QrPrNlqqFGpl7RrqsqNPmoZhp8MSdIcTSNKUhXmnELmL7h0C1bV807d4r4qqy/wByvU/lCJFBHJh55xr3wHan6hllV1X7T1WN0rGybxWYKZtWzb1TJ6JPLmIj0RmgiOOPK89IYTMaVz3lcQhSk37KCqx7TeApXphlB1Y9pvAUr0xIVfLQqxqa114uVFSb0raTIlJpZxkUKBGYXROIQpNI0pM5PeNpsM9dty8wNor5Z5ZtTVQvFOoqNellSZEpDMNMihTozy6RxSEI400pc5RIOT5Wd2Z8fRfUF/iAbUe3Zpt14VENUsMddtx8wNVtR7CWm3XikQgAAFV2Geu24+YGqWo9hLTbrxSIQAPVQAAAAZVaj2EtNuvFIhAA9VBANqPbs027cKiGUgA9VBAFqPbs0268KiGq2GOu24+YGq2o9hLTbrxSIQAPVQBKtufqTv3lxldl3bqzO9cKlF/DysHIDVLLu3Vmd64VKL+ABK1ubqTv3lxKwD1SABlVqMn2EtNuvFIhAAD9CqbDPXbcfMCqQAQDaj27NNuvCohqlhjrtuPmBqtqPYS0268UiEAAAquwz123HzA1S1HsJabdeKRCAB6qAAAADKrUewlpt14pEIAHqoAAACALUe3Zpt14VENVsMddtx8wNVtR7CWm3XikQgAeqgAMqtR7CWm3bikQgAB+hVNhnrtuPmBVIDytAcDgB6qAAAPKsX/Zc2EszvXFJRqoyq1HsJabdeKRCAB6qCAbUe3Zpt14VENUsMddtx8wKqAAAZVaj2EtNuvFIhAA9VAAAABlVqPYS0268UiEADVcoOs/tN4Ci+mOcoKs/tN4Ci+mGUFWf2m8BRfTDKCrP7TeAovphlBVn9pvAUX0wygqz+03gKL6Y6A1DQLRqV7SVwvqVellSYZqaWajihNIaTRNIQhNE0hMxBSdhjrtuPmBqtqPYS0268UiEAD1UEgV81wt0y1a68U6hXl0VtGkSkN0QJIYkCM8ukcYUpdI4pc5ekd/spVgtO3eNGNSzv8AcrrI5OiRQRzouYaa98But+odrtR7CWm3bikQgAX/AJPlWHZnx9K9QSFXyzysZatdeKZQ0a6K2jSJSGYcfDEgRnl0jilKXSOKXOUbRYZL/LbcvMDaa+WhWbLVULxcqKlXRZUaRKTSzT4Yk6MwuicQpC6JxSZydIkHKDrO7TeAovpiqsnyrDsz4+leoJCr5Z5WMtWuvFOoaNdFbRpEpDMOPhiQIzy6RxSlLpHFLnKO/wBlOr5mG7xoxqVl/uV1kcoSooI50XMONe+A3W/UO/V81PsKytVK8XKhUd0WVGkSk17TpIYk6MwuieeUhdE4pM5OkSEPVISBXzXC3TLVrrxTqFeXRW0aRKQ3RAkhiQIzy6RxhSl0jilzl6RoFlKsFp27xpxqWd/uV1kcnRIoI50XMNNe+A3W/UO/V8tAs2WqoXi5UVKuiyo0iUmlmpIYk6MwuicQpC6JxSZydIkLKCrO7TeAovpiqcnyrDsz4+leoMArRrBaeqxulmxrBrPBTNq2VdKHd0SeXMRGJT9NKaceV56Q8uc4rnuJmIQg1WylWC07d40Y1LO/3K6yOTokUEc6LmGmvfAbrfqHarUewlpt14pEIAHqoIBtR7dmm3XhUQ6qwtYLTsJfsVVncL7BP5OiSxwRQ8805zoztTtY7VlBVndpvAUX0gygqzu03gKL6QZQVZ3abwFF9IMoKs7tN4Ci+kGUFWd2m8BRfSHy2prhbpqlFSVMvl5elbSYZqG6IDIoTiHm6RphCkzmkLmKM9HqoAAAAOq1pMjj2wizZy/XC+yuUSpsECUxJzYjXvgdrJrE/wCSN32+E/ODJG77fCfnDnJH77fCfnBkj99vhPzhxkjd9vhPzgyRu+3wn5wZI3fb4T84c5I/fb4T84arUZVH7LMN/XeFcJSPwkiXLmfnOe+Z7nO947VWiyOPbCLNnL7cL7K5RKmwQJTEnNiNe+B2smsT/kjd9vhPzhzlcdyfi3yQ9knt1+0bDeAsNfq+6XqTJ+g+9jMifKi5pHROzueXVKi6o/ZZhv67wrhKR+EkS5cz85z3zPc53vC1HsJabduKRCAB6qDAK0bOuPTdLNo8abhfZXJ8HzYIERiPnTTXvgfqJrHa6jKo/ZZhv67wrhKR+EkS5cz85z3zPc53vHaq0WRx7YVZs5fbhfZXKJU2CBKYk5sRr3wO1k1jAMkfvt8J+cKpEA2o9uzTbrwqIapYa67bl5garaj2EtNu3FIhAAqrK47k/Fvkjn2Se3X7RsN4Cw1+r7pepMn6D72MyJ8qLmkdE7O55dUqMqj9lmG/rvCuEpH4SRLlzPznPfM9zne8LUewlpt14pEIAHqoIBtR7dmm3XhUQVGVueyzDf1JhXCUj8XIly5n5Dnvme5zveO11o2ise2EWbOYr3C+yuUYQmwQJTEnNlGvfA7WTWJ+HqoMArRs649t2s2jxouF9lcnwfNggRGI+dNNe+B+omsdVyR++3wn5wZI/fb4T84Mkfvt8J+cGSP32+E/ODJH77fCfnBkj99vhPzgyR++3wn5w4yR++3wn5wqoAAAAAAZ81NcLCsqvaSpl8vLqsqNBNQ3ROfDEaQ8mkaYUhdE4hcxekfXYasJmG7vuKizv9ygn8nSooI4oeeaa98B2p+odqAZVlB1YdpvAUr0wyg6sO03gKV6YZQdWHabwFK9MMoOrDtN4ClemGUHVh2m8BSvTEBC/bLuwpmd54pKO1tzWCzDCXLGpZ3C+xyOTpUscEMXMNOc6M3W7WMUr4rhYVqaqF6p1CvL0sqTIlIbonMihTozy6RxhCE0TSlzlEgC/wDKDqw7TeApXpjv7LNAq2pUVGXChpV6VtJilJpZyOKE4phdE4hCk0jSkzk6B8tuawWYYS5Y1LO4X2ORydKljghi5hpznRm63ax8hlq4mFale0ZTqFeXpZUmOUhuicyKE0487SOMIQjjTSlzl6BoIyrKDqw7TeApXpiQa+WgVjU1rrxcqKk3pXUmRKTSzjIoUCMw7ROIQpNI0pM5OgbTYa67bl5garaj2FNNu3FIhAA1XJ8rP7M+PovqDf6r6wWYqsYVWMa3izwU0itm3uhyEqeXMSnpTNNEacYV5iQwuY4rnuK4pCkGqsNWCzDd33FRZ3+5QT+TpUUEcUPPNNe+A7U/UOq2o9hLTbrxSIQAPVQSDXxU+3TVVrrxcqFR3pW0mRKTXxAZFDR0ZhdE48hSaRpSZydAz/J9rO7M+PovqBk+1ndmfH0X1AyfazuzPj6L6gqnKCqx7TeApXpjv7LNAq2pUVGXChpV6VtJilJpZyOKE4phdE4hCk0jSkzk6B8tuawWYYS5Y1LO4X2ORydKljghi5hpznRm63ax1XKCqx7TeApXpjjKCqx7TeApXphlBVY9pvAUr0wygqse03gKV6YZQVWPabwFK9MfUZauFhWpXtGU6iXl6WVJjlIbonMihNOPO0jjCEJomlLnL0DQQAAAAAAEAWo9urTbrwqIarYY67bj5gVUA8qwAAAX/Zb2FMzvPFJRlVufqTv3lxKoAL/subCWZ3rikoyq3P1J37y4yqy5t2ZneuFSi/x5Vj9CqLDXXbcvMDVbUewppt24pEIAHqoIBtR7dWm3bhUQ1Wwz123HzA1S1HsJabduKRCAB6qAAAPKsX/Zc2EszvXFJRlVufqTv3lxKoAADVrLm3Zmd64VKL+AAAAAAAAAB5Vi/wCy5sJZneuKSjKrc/UnfvLjKrLu3Vmd64VKL/AQBaj27NNu3CohlQ1Wy7t1ZneuFSi/x5Vi/wCy5sJZneuKSjVRlVqPYS027cUiEAD1UABlVqPYU027cUiEAD1UEAWo9urTbtwqEZYA/I9VBAFqPbs0268KiGVDVrLm3Vmd54VKL+HlWL/subCWZ3rikoyq3P1J37y4yqy7t1ZneuFSi/x5Vi/7Lmwlmd64pKNVGVWo9hLTbrxSIQAPVQAAAAZ9Xy0CzZaqheLlRUq6LKjSJSaWafDEnRmF0TiFIXROKTOTpEhZQVZ3abwFF9MMoKs7tN4Ci+mK9qGaFZtTVQo1yvaVe1lSZ81NLNRxQp0hhuiaQhCaJpCZidA0EgAMqyfasOzPj6V6gwGtGsFp6rG6WbGsGs8FM2rZV1od3RJ5cxEYlP00ppx5XnpDy5ziue4jiEIQdqqNL7dcN+1T69wLIuH4WTOmTPuIInykfOe6HM55X9prQq+Ziq1hVm2TBqzBTSK2VdKZeEqeXMSmIj9BKccYV5iQ8mc0rnvJnIQowDKDrP7TeAovpi/xAFqPbs027cKiGVD67LtAtGVXtGXChpV1WVGilJpZqSGI0ph2icQpC6JxSZydI7/lB1ndpvAUX0xVOT5Vh2Z8fSvUHf2VZ9VsqoqMplDRboraNFKQzDkkMRxTy6RxSlLpHFLnL0jFrVtYLTsJiviqs7hfb1P5OiSxwSYeeac50Z2p2sTY1FcLdNSoqSp18vL0raTDNQ3RAZFCcQ83SMMIUmkaQuYvQM9HqoJAr5rhbplq114p1CvLoraNIlIbogSQxIEZ5dI4wpS6RxS5y9I0CylWC07d4041LO/3K6yOTokUEc6LmGmvfAbrfqHarUewlpt24pEIAGq5QdZ/abwFF9MdBaloFm1S9pK5X1KvSypMM1NLNRxQmkNJomkIQmiaQmYg+UA/I9VBnzU1PMK1K9pK5XqivSypMM1Ne05kUJpDCaJp5CE0TSEzE6BNlqyr5mGExXxVVlwvt6vHKEqWOCTDzzjnOjO1O1jqtl3NXqzO9cKlF/DyrGgstXE3TLKKjKdQry6q2jRSkN0QHwxHFPO0jjClK844pc5ekbVUZ9uuG/ap9e4FkXD8LJnTJn3EET5SPnPdDmc8r+01oVfMxVYwqzbJg1ZgppFbKutMvCVPLmJTER+glOOMK8xIeTOaVz3kzkIUYBlB1n9pvAUX0xVWT5Vj2Z8fSvUHf2XZ9WMsoqMp1DRrqraNFKQzDj4YjinHaRxSlK8pxS5y9Ixa1bWC07CYr4qrO4X29T+TokscEmHnmnOdGdqdrE2NTXC3TVKKkqZfLy9K2kwzUN0QGRQnEPJpGmEKTSNIXMUZ6PVQAAAAZVak2FNNu3FIhAAqrJH77fCfnDn2uewr7OcCYdwL+sL3dZ076f7qA+F02HnFfC/M9xNUqLrc9qeG/qTBWDZH4ufMmTPyGudL973+4drrSa7ERhFm0dyv9ylcnmyo40piPnQnOdG/UXUJ/wArnuT8W+SGVz3J+LfJD2R+3X7RsN4Cw1+r7pepMn6D72MyJ8qLmkdE7O55dVqMqj9lmG/rvCuEpH4SRLlzPznPfM9zne8dqrRZHHthVmzl+uF9lcolTYIEpiTmxGvfA7WTWMByR++3wn5wZXHcn4t8kT/Wk1uPbdrNo7lcL7K5PNmwQIjEfOhNe+B+omsdrqMqi9qeG/rvBWDZH4SfMmTPzmudL973+4arkj99vhPzgyR++3wn5wZXHcn4t8kb9Vc12PTCqxo7lcL7N5PNmwQJT0fOhNe+B+omsYDbm6k795cYBVcyOPbdKxnL7cL7N5RKmwQIj0nNiNe+B2smsb/kjd9vhPzgyuO5Pxb5IeyT26/aNhvAWGv1fdL1Jk/QfexmRPlRc0jonZ3PK/RY71Yy7jdrv+8jivHudD0vzdVrRtFY9sIs2cxWuF9lcowhNggSmJObKNe+B2smsT8A/Q1SoyqP2p4b+u8FYNkfhJ8yZM/Oa50v3vf7hquSP32+E/OHGSP32+E/OFVDAK0rROIjdLJnMV7/AHKVyjCEqONEYk5so5zo3ay6h1Qn/tT91cWt+vN5/dwQ3f3vi6HZ+11XWdcRG6VjR40X+5TeT4PlRxoj0fOmnOdG/UXUN/Eq5I3fb4T84c5I/fb4T84P0WO9WMu43a7/ALyOK8e50PS/M9rnt1+znAmAsNfrC93qTJ+n+7gMifKh5xHRPzucVkj99vhPzgyuO5Pxb5I36q5rse2FVjR3K4X2byebNggSno+dCa98D9RNY6tXnVH7U8CfXeCsGz/wk+ZMl/nNc6X73v8AcMArRs7YiMKs2jxov9ylcnwfKjjSmI+dNOc6N+ouoT+PVMAAAAZVai2EtNuvFIhAA9VBAVqPbq027cKiHabKdYLMMJjRjWs7hfbrd+TpUscE6LmGnOdGbrdrHf6+K4WFaqqheqZQry9LKkyJSG6JzIoU6M8ukcYQhNE0pc5RILhqmT5Wf2Z8fRfUFAVXVgMxVYwqsY1vFngppFbNvdDu6VPLmJT0pmmiNOMK8xIYXMcVz3FcUhSE1VhawWYbu/YqLO/3KCfydKigjih55pr3wHan6h9RqGgVjLKKkrhfUq6q2jQzU0s5JDEcQ03RNIUpc5xCZidI6BlB1Y9pvAUr0xAQ7+y1T7dNSo6MuFCo72raTHKTXtAZFCccYXROPIUmkaUmcnQNqqM+wnDftV+osNSLh+KnSZkz7iOF01HznPizPcV2q5QVWPabwFK9MMoKrHtN4ClemJVyfazuzPj6L6gr2oZn1my1U6jUy9o10WVGnzUMw1JDEnSHE0jSlIXROIXMUYvbm6k795cZVZc27MzvXCpRfw8qxX9Q1cDCsrVQo1Mvl5dFlRp81DdE6SGJOkPJpGmFIXROIXMXpHQLVlYLMN3ivios7/cr1P5OlRQRyYeeaa98B2p+oT+AAKrsM9dtx8wKSahoFYyqipS5X1Kuqto0M1NLOSQxHENN0TSFKXOcQmYnSOg5QVWPabwFK9MaqJAr4qebpqa116uFCo70raTIlJr2gMihQIzC6Jx5Ck0jSkzkGgWUqvWnYTGjGpWXG+3WRyhEljgnRcw45zozdbtY2lqGgVjLKKkrhfUq6q2jQzU0s5JDEcQ03RNIUpc5xCZidI6BlB1Y9pvAUr0wyg6se03gKV6YZQdWPabwFK9MZXXn9u2BPZV9e4Fn3/8ACyZ0uX9/BE+Uk5r3Q53PI/5VQ9TzdMrWuo1yvlJdFbRp81Ne0CSGJAkMJomHlKXSOITMQV8PKsX/AGXNhLM71xSUdrbmsFmGEuWNSzuF9jkcnSpY4IYuYac50Zut2sYpXxXCwrU1UL1TqFeXtZUmRKQ3ROZFCnRnl0jzCEJomlLnL7hIA9VAAAABlVqLYS0268UiEAD1UEBWo9urTbtwqIZUOByPVIQDaj26tNu3CohqthnrtuPmBqlqPYS027cUiEAD9C/bLmwlmd64pKMqtz9Sd+8uJVAeqgCVrc3UnfvLjKrLm3Zmd64VKL+HlWOQHAAAquwz123HzA1S1HsJabduKRCAB6qA4BlVqPYS027cUiEAAAquwz123HzAqkB5Vi/7Lmwlmd64pKMqtz9Sd+8uJVAeqgAAAAyq1FsJabdeKRCAB6qCArUe3Vpt24VEMqGqWXdurM71wqUX6OQAAAAAB5Wj+tVq6nLanoqCqqHSabTUr5dHoyI5IkPcQpSuNNIUpXEIUv7CDSaLUBWVSUJqUjOyzTiEKQiWmIDTs/8AOQp7yfsLnGiVEVNNwylaqkXS9VBlHV1FnzUpKUhPdEgSGEzGnFKXSOJ0CugEi171Ntw1daq7XSiVKOkK6lSJSQtKRGFLCgRmFzHHEKTSNKQdBJZ3rK/qJF/jkH8Y79UTU03DKVqqRdLxUo6OrqLPmpSUtEfDEgSGEzGnFKXOcQmrpFdCBsnesv8AqJF/jkH8Y+auaj6xlRRS0iksvS0qIh0LqIkR0k8pf/4Izjjne9w2Kwz123HzAqkB5Vi/7Lmwlmd64pKMrtzdSd+8uJWH5ABVdhnrtuPmBVIAIBtR7dWm3XhUQ1Wwz123HzA1S1HsJabdeKRCAB6qAAAADKrUWwlpt14pEIAGq5QdZ/abwFF9MUBVfV8zFabCqxsm8VmFWkWU290y8JUEyWlPRGaCI40wjjEZhMxpHueV5SlKMqtWVfMwwmK+KisuF9vV45QlSxwSYeecc50Z2p2sdVsu7dWZ3rhUov0ciQK+a4W6ZatdeKdQry6K2jSJSG6IEkMSBGeXSOMKUukcUucvSNAspVgtO3eNONSzv9yusjk6JFBHOi5hpr3wG636hv4AJAr5rhbplq114plCvLoraNIlIbogPhiQIzy6RxhSl0jilzl6R3+ylWC07d40Y1LO/wByusjk6JFBHOi5hpr3wG636h3+vpoVmy1VC8XCipV0WVGkSk0s0+GJOjMLonEKQuicUmcnSJCyg6zu03gKL6YzZUK6lLda0JWq9HNplMTGUdAjiIbGkPOIaaR5SkIR5SkzlKQg9A6s2BU1WLN0ahK+jGUlcJzSXmlQkm0lLmi0v/dRkdmJqIQnScUpTu6ITVkeQpyRLRERClzGERHHlIT+Z8RH/wBhB/pLpzv+00b9wd/GEun/ANJov+HO/jCXT/6TRf8ADnfxhBT/AOk0X/DnfxhBT/6TRf8ADnfxgVHT/wCk0X/DnfxjiXT/AOk0X/DnfxjmXT/6TRf8Od/GP8kp6xo5SnlNQUpETWYiNKYeT3keUpC/szD/AAVKpVCBY05cquhoENLWZqMlKTIjYSp5ZT4Snk1REjOI8pH6iFLmIQnV6+WgWbL1ULxcqGk3RZUaRKTSzT4Yk6MwuicQpC6JxSZyCQMoKs7tN4Ci+mKqyfKsezPj6V6g7+y7PqxlVFRVMoaLdVbRopSGYckhiOKcdpHFKUrynFLnL0ibLc3UnfvLjFqhWfVjU1rqNTr6jXtXUmfNQzDzIoUCQ8mkaUhSOONNLmKK9yfasezPj6V6ggABVdhnrtuXmBtFfLQLNlqqF4uVFSrosqNIlJpZqSGJOjMLonEKQuicUmcnSJCygqzu03gKL6YZQVZ3abwFF9MdAahoFo1K9pK4X1KvSypMM1NLNMihNIYTRNIQhNE0hMxBSlhnrtuPmBqlqPYS0268UiEAD1UAAAAGV2othTTbtxSIef45G/1X2isRGFVjOYr3+5TeUYQlRxpT0nNlHOdG7WXUO1/pT91cWt+vN5/dwQ3f3vi6HZ+1VXWdcRG6VjR40X+5TeT4PlRxoj0fOmnOdG/UXUN/cJWyuO5Pxb5IeyT26/aNhvAWGv1fdL1Jk/QfexmRPlRc0jonZ3PK/RY71Yy7jdrv+8jivHudD0vzdqqutFY9t0rGcxXuF9m8owhNggRHpObKNe+B2smsb+JWyuO5Pxb5IwCtFrse27WbSXK4X2VyebNggRGI+dCa98D9RNY7VUZW57LMN/UmFcJSPxciXLmfkOe+Z7nO947VWlaKx7YRZs3ivcL7K5RhCbBAlMSc2Ua98DtZNYn8atZfQGUivBnCJDTTjTLwkIQ415HkQJHF/aQrikL7hdppkS7SHlK8iOjmENIXoiOOeUn7YSf2D+8AAAAAAfwqwyWlpxhOaSkFKab/ADPMNKV3/UpS/wDUR9WvaGx2YtbMxixcb0cjNvOEJsMtKYfzZRHvgdr6RPg9VBP9aVorERulmzmK9/uUrlGEJUcaIxJzZRznRu1l1Dq36U/dXFrfrzef3cEN3974uh2fj2R+wontGw3h3Av6vul1nTvoPvYz4XTYuaV8Lsz3kZXPcn4t8kSsN/qus649sKrGjxouF9m8nwfNggSno+dNNe+B+omsb/UXVH7LMN/XeFcJSPwkiXLmfnOe+Z7nO94WpNhTTbtxSIQAAoCq6zrj2wqsaPGi4X2byfB82CBKej500174H6iaxv8AUXVH7LMN/XeFcJSPwkiXLmfnOe+Z7nO947XWiyOPbCLNnL9cL7K5RKmwQJTEnNiNe+B2smsYBkj99vhPzhVIAAAAz6vlnlm1NU69Uyio16WVJkSkMw0yKFOjPLpHFIQjjTSlzlEg5PlZ3Znx9F9QZUNBZep5umpUVGXKhUd7VtJilJr2gMihOKYdonHkKTSNKTOToFKWUqvmnYTGjGpWXC+3WRyhEljgnRcw45zozdbtY2lqGgVjKqKkrhfUq6q2jQzU0s5JDEcQ03RNIUpc5xCZidI6BlB1Y9pvAUr0xAQr6oeuFhWVqoUamXy8uqyo0+ahuic+GJOkPJpGmFIXROIXMUfKrz+3bAnsr+vcCz7/APhZM6XL+/gifKSc17oc7nkf8moap9umVrXUa5Xyjuito0+amvaA+GJAkMJomnlKXSOITMQV+QeVo7+y1TzdNSoqKuVCo70raTHKTXtAZFCcUwuiceQpNI0pM5OgfKbmr5p2EuONSsuF9jkcoRJY4IYuYcc50Zut2sdVH5FMVAVQtyylaaoXC/Ud0VyA1MRIlvaA+EpyE80mY08pdZSEzEFXmf8Ae9Iz/wDgIv8AMkH9DweP08fz0qkoaKgPTUg8iNEYR5Sl/wDWv3Dq6dsilSFJRqEU5GQvOSJISl97iE//AGPpqVoUCySEQpDTkFJLnIYcV5Dv53FzP/YUhCj7cQPB4PH89A/7QsP+OT/SMHn419Ubbs4qqevFypLsq6OeQqRPe0B8JDjyGm6Jp5TivKcQmYnSM8HqmJAr5qebpqq2F6uFCo70raTIlJr2gRxQoEZhdE48hSaRpSZyD61Rn2E4b9qn1FhqRcPxU6TMmfcRwumo+c58WZ7iu+rXxXAwrVVULxTKJeXtZUmRKQ3ROZFCnRnnaRxhCE0TSlzlEfjVcnys/sz4+i+oKAqvrAZiqxhVYxreLPBTSK2be6Hd0qeXMSnpTNNEacYV5iQwuY4rnuK4pCkJqrC1gsw3d+xUWd/uUE/k6VFBHFDzzTXvgO1P1D5FfLPLNqap16plFRr0sqTIlIZhpkUKdGeXSOKQhHGmlLnL0CQsn2s7sz4+i+oGT7Wd2Z8fRfUFfVDM+s2WqoUanX1Guiyo0+ahmGJIYk6Q4mkaUpC6JxC5ij6rc1gswwlyxqWdwvscjk6VLHBDFzDTnOjN1u1j5LLVwsK1K9oynUS8vSypMUpDdE5kUJpx52kcYQhHGmlLnL0DQQAAAAAB5Vi/7Lmwlmd64pKNVGVWo9hLTbtxSIQAACqrDPXbcfMCqgHlWL/subCWZ3nikoyu3N1J37y4lQB6onazf/XQP4v1xSP+Ai/zJB/u8gPIOYh0ptKacnpplDNO+jQmkOOITpOL/sT/AMyjrzweUhTSmHQnmlIcacToKTOQo0pU02+q6j0h2keaQpxCdBSZi/8A9kH9jxw8gPIP8laV6dYf8cn+mYMwtLbBWm3XikQggeqgCVbc/UnfvLiVQHqoIBtR7dWm3bhUQ1Wwz123HzAqkAASrbm6k775cZXZc27MzvXCpRfwAAAADKrUewlpt14pEIAHqoIBtR7dWm3XhUQysB+R6qAAAPKsciqbDHXbcfMCqgH4O1m/+ugfPP8A++aR/wABF/mSD/cByM/achTV5Sn9MJf/APJB8wB3xlSFNUdHeTXEUn7Iij6wAPwqvvlh/wAcn+kYMxtLbBWm3XikQggBf9lzYSzO9cUlGVW5+pO/eXEqgA5HA1ay5t1ZneeFSi/gEAWo9uzTbrwqIarYZ67bj5garai2FNNuvFIhAQ9UgAAAB8lqFArGpUdJU6+ot6VtJhmoZhyOKE4h5NI0pCk0jSFzF6B0DJ9qx7M+PpXqCVsoKs/tN4Ci+mOgNQ0Czape0lcr6k3pZUmGamlmo4oTSGE0TSEITMaQmYg+UA/I1XKDrP7TeAovphlB1n9pvAUX0xQFlOsFp27xoxqWd/uV1kcnRIoI50XMNNe+A3W/UN/AZVk+1YdmfH0r1BIVfLPKxlq114p1FRboraNIlIZh58MSBGeXSOKUpdI4pc5RtNhjrtuPmBVICRKgq3m4aytRUKhoF3e1cnNTlSIbogRxQoTziZzTCF1kIXMXoFTpMy5T/wDARf5kg/2eDwiHW2sVx9IMNpSEwpx6Mjj3aymv1jqL3D+tXUJNT6SahQm+847oNJ/ONDo5hqBAjRIyOMMNIaaT3EH7iHLwePwqM6VYF/8Ark/0jBH9WrdtJWZWDQWKbdZYTZhYHpiUmhSESGZKRnpTNNGaaeRx6MwuY4j3OK8hSkG+ZPtWPZnx9K9QQANBZauJumWUVGUyiXl1VtGilIbogPhiOKedpHGFKV5xxS5y9I+U3VYLTt3ccalnf7lHI5OiRQRwxcw0174Ddb9Q6mAv/J8qw7M+PpXqCQq+WeVjLVrrxTqKi3RW0aRKQzDj4YkCM8ukcUpSvOOKXOUZ8NVsu7dWZ3rhUov4BAFqPbs0268KiGq2Geu24+YGq2othTTbrxSIQEPVIAAAAHAJWyR++3wn5wwCtFkcRG6WbOX6/wBylcolSo40RiTmxHOdG7WXUO1VGVR+1PDf13grBsj8JPmTJn5zXOl+97/cO11o2dcRGFWbR40X+5SuT4PlRxpTEfOmnOdG/UXUJ+HI3+q6zrj2wqsaPGi4X2byfB82CBKej500174H6iax2r9FjvVjLuN2u/7yOK8e50PS/N2uq60Vj23SsZzFe4X2byjCE2CBEek5so174Hayaxv4CAbUW3Zp914VENVsM9dtx8wN/rSa7ERhFm0dyv8AcpXJ5sqONKYj50JznRv1F1Cf8rnuT8W+SO2VWWesQW5oDQ4zYQupqQ274PlRRozjHxTSudE/V0DZ6QX65Tf8ui/zJB/o8Hj/ACpNKQ0VEVJSEpiMwnSec4fBpjVUcjzaIgSp8xSRl0Df2k6S/wBn/UdTIV4+kpFrgpKkKVCclNSEIQsJzikd0+/X7h2pXrug046BEmgS/wDy0hISj6ZCg8Hj9Kb7xYf8wT/TMEzFqq9iaRJWXhnDeCDinYNut2mz/oPvYz4YZ0XNK+F2Z7yc5XPcn4t8kcZI/fb4T84CWR++3wn5wyuvOqP2WYE+u8K4Sn/hJEuXL/Oc98z3Od7xlICqsrnuT8W+SOS1Se3X7RsN4Cw1+rrpepMn6D72MyJ8qLmkdE7O55cqrzqj9lmBPrvCuEp/4SRLly/znPfM9zne8dVqua7ERulY0dyv9ym8nmyo40R6PnQnOdG/UXUN+yue5Pxb5IZXPcn4t8kc+yP27faPhvAWGv1fdL1Jk/QfexmRPlRc0jonZ3PK/RY71Yy7jdrv+8jivHudD0vzdUrStFY9sIs2cxXuF9lcowhNggSmJObKNe+B2smsT+PVQAAAAfJahoFYyyipK4X1Kuqto0M1NLOSQxHENN0TSFKXOcQmYnSOgZQdWPabwFK9MaqIBtRbdmm3XhUQ1Swx123HzA1W1HsJabdeKRCABquT5Wf2Z8fRfUFAVX1gMxVYwqsY1vFngppFbNvdDu6VPLmJT0pmmiNOMK8xIYXMcVz3FcUhSE6rXmT264E9lf17gWff/wALJnS5f38ET5STmvdDnc8j+q1XVftPVY3SsbJvFZgpm1bNvVMvCJPLmIj0RmgiOOPK89IYTMaVz3lzEKUm/ZQdWPabwFK9MMoOrHtN4ClemMBrQq+aetNulm2TBqzCrNrKVdKZPRIJktEYiP0Epxp5HHozyZzSPc8jyFIUuqWUqvmnYTGjGpWXC+3WRyhEljgnRcw45zozdbtY7Vaj2FNNuvFIhAA9UTucb+0fJpRXLpP/AMui/wAyQfspXDri5aOSeegVxDEiU3MclLnNNL/N7/8AyHV06RJSUs2kpD0yX/4ji6v2dA/JRy8Hj8nkIeTSIPqqxe0yguNSlLSkGqE4ukb+wo7cr1hRqehKkoySIhMxSFzFN/aToH9bx+1IV56wL/8AcE/0zB0yu9QLNqKol2plFRr0sqTIlIZhhkUKdGcdpHFIaRxDTi6+hwkYtn2s7sz4+i+oKqyg6se03gKV6Q78yzQqtqVFRlyoaTelbSYpSaWcjihOKYXROIQpNI0pM5OgYvatq/adu8V8VVZf7lep/KESKCOTDzzjXvgO1P1CbGpqebpllDSlyvlHdFbRoJqa9oD4YjjTCaJp5Sl0jiEzE6Rn41TJ9rP7M+PovqDf6r6wGYqsYVWMa3izwU0itm3qh3dKnlzEp6UzTRGnGFeYkMLmKVz3FzkKQnVa8/t1wJ7K/r3As+//AIWTOly/v4InyknNe6HO55H5Vk+1ndmfH0X1AyfazuzPj6L6gykX/Zb2EszvXFJRlVufqTv3lxNjLs+tGpXtGU6hot6WVJilIZhqOKE0p5dI4pCE0TSlzlHf8n2s7sx4+i+oL+AAAAGVWo9hLTbtxSIQAPVQQDai27NNuvCohqlhjrtuPmBqtqPYS0268UiEAD1UEA2o9urTbtwqIarYZ67bj5gapai2EtNuvFIhAAC/7Luwpmd54pKNVGVWo9hTTbrxSIQAPVE7nG/tHx6YVy6Tf8ui/wAyQf50sp11TFRvjgLC7+d3+4zg1xDDSEJqH7e4cRjh4PHLwePsMkQ7Cx0GoqEsX9pB3J4/1URdJYf8wT/TRj+6hl+iIP8AceVYv+y5sJZneuKSjVXDKrUeaolpt14pEIBHqmIAtR7dWm3bhUQ1Wwx123HzAqoB5Vi/7Lewlmd64pKMqtz9Sd+8uMrsubdmZ3rhUov4AAAAAAeVYv8AsubCmZ3rikoyq3MT+RO/eXGV2XNurM71wqYX84AEq25+pO/eXGVWXNuzM7zwqUX+ACVrc3UnfvLiVAHqidzjf2j41O/77Tf8ui/zJBw8fwYHV39FN/vHf7jjA6u/ohP7xf8AcMDq7+iE/vF/3DA6u/ohP7xf9wwOrv6IT+8X/cMDq7+iE/vF/wBwwOrv6IT+8X/cf0USh0eiFOLR0JqMpznlIUv/AOylH9Dx/uoc5yw/5gn+mYPMim5qYn/4h3/mP8B6qAJWtzdSd+8uMqsu7dWZ3rhUov4gCALUe3Vpt14VEMqGrWXNurM7zwqYX8PKsAAB6qAAAADPq+WgWbLVULxcqKlXRZUaRKTSzUkMSdGYXROIUhdE4pM5OkSFlBVndpvAUX0xVWT7Vj2Z8fSvUHfmXZ9WMqoqMplDRbqraNFKQzDj4YjinHaRxSlLpHFLnL0j5TdVfsw3dyxqVl/uUcjlCVFBHDFzDjXvgN1v1DK60KvmYqsYVZtkwaswU0itlXWmXhKnlzEpiI/QSnHGFeYkPJnNK57yOKQhRP8AlB1n9pvAUX0xf4kCvmuFumWrXXinUK8uito0iUhuiBJDEgRnl0jjClLpHFLnL0j61Rn27Yb9qn17gWRcPwsmdMmfcQRPlI+c90OZzyv7VWhV8zFVjCrNsmDVmCmkVsq60y8JU8uYlMRH6CU44wrzEh5M5pXPeRxSEKMAygqzu03w+i+mGUFWd2m+H0X0wygqzu03gKL6Y6q3NYLTt3csa1nf7lHI5OiRQRwxcw0174Ddb9Q6mAoep6vNslpWYz6valenUlU0ykXZIiNoSA0px6Q0pqPOYYQpCTCmPcXV7swrJbGlRLFGn/8ADSoyIn/zHGlKUhP+sRf7B+AeAAAAA/Jx5pppTjikIQmspRwSnIVGza0XSwjNoqBGlpyUhprziIzDM7idJYTHu948zzzypDzjztZxSlL+0fgeqgCVrc3UnfvLjKrLu3Vmd64VKL+IAgC1Ht1abdeFRDKh9dl2gWjKr2jLhQ0q6rKjRSk0s1JDEaUw7ROIUhdE4pM5Okd+yg6zu03gKL6YqrJ8qw7M+PpXqBk+1YdmfH0r1AyfasezPj6V6gz+vmp9hWVqoXi5UKjuiyo0iUmvadJDEnRmF0TzykLonFJnJ0iQh6pAAAADqtaTI49sIs2bvtwvsrlEqbBAlMSc2I174HayaxgGSP32+E/ODK47k/FvkjgtrjuT8W+SNUqMrc9qeG/qTBWDZH4ufMmTPyGudL973+4c2othLT7rxSIQAKqyue5Pxb5I59knt1dWNhvAWGv1fdL1Jk/QfexmRPlRc0jonZ3PK/RZ71Yy7jdrv+8jivHudD0vzdVrRtFY9sKs2cxXuF9lcowhNggSmJObKNe+B2smsT8AoCq6zrj2wqsaPGi4X2byfB82CBKej500174H6iax1WvOqP2WYE+u8K4Sn/hJEuXL/Oc98z3Od7xlID9D0KqTaektbVSoVi0JUaSlp0R6NKe5xqWWlORxFz84pDCFL0Pe52odzwOYXOgplKRIy6jTSmnEJ/eNKUMDF/rGmf2Iv4AwMX+saZ/Yi/gDAxf6xpn9iL+AMDF/rGmf2Iv4AwMX+saZ/Yi/gHGBi/1jTP7EX8AYGL/WNM/sRfwBgYv9Y0z+xF/AP5FkRTqOi31oFoiQUU04hJtPTmIkRDugheaaUv7f+gyJoF3R7QNEXDLsguEitUlAPQH0umJKGU86mnHFPKaYaaU80pphCoyHFKcR5xXZjSGlKd1M2yQ8n8tvhXzgyR++3wn5wqoBK1ubqTv3lxgFVzXYiN2rGjuV/uU3k82VHGiPR86E5zo36i6hv+Vx3J+LfJDK47k/Fvkjj2R+3X7RsN4Cw1+rrpepMn6D72MyJ8qLmkdE7O55crrzqj9lmBPrvCuEp/4SRLly/wA5z3zPc53vHVarmRx7bpWM5fbhfZvKJU2CBEek5sRr3wO1k1jfskjvt8J+cOcrjuT8W+SN/qua7HthVY0dyuF9m8nmzYIEp6PnQmvfA/UTWO1DKrUWwlpt24pEIAHqoAAAAPktQ0CsZVRUlcL6lXVW0aGamlnJIYjiGm6JpClLnOITMTpHQMoOrHtN4ClemJWyfazuzPj6L6gZPtZ3Znx9F9QarUZ9hOG/ar9RYakXD8VOkzJn3EcLpqPnOfFme4ru01oVgsxWmwqzY1g1nhVpFlKulDkJUEyWlMSn6aU00wjjEZ5c5xHucR5SkIXAcn2s/sz4+i+oMqKK/qGrhYVlaqFGpl8vLqsqNPmobonSQxJ0h5NI0wpC6JxC5i9I+VXn9u2BPZX9e4Fn3/8ACyZ0uX9/BE+Uk5r3Q53PI/FmpqebplVDSlyvlHdVbRoJqa9oD4YjiGE0TTylLpHEJmJ0jPQF/wBlzYSzO9cUlHVbVtXzTt3iviorL/cr1P5QiRQRyYeeca98B2p+oT/k+VndmfH0X1AyfKz+zPj6L6g5yfazuzPj6L6grWpBllkzlU6jUy+ot1WVHnzUUZp8MSdIebnNKUhXmnELmL0js6dSmpDiuNHQ65GLWi7q4W6uZ+jH0hZppMlGRIajihTGHHaRxSEzGkLrL0CY/YXWr/UCf/8AIUf1BZ+L35DhMlclT7fryshbrFnlMlpCsTSZSUlMQo3uQmGnaJx5Ck0iF1k947pZzq0apmcYcblcfRLxd7vFSEaWKGbFzDjnOiN1u1jZ8X/yl/sKGL/5Tv7CiMPYVWr2fT//AJGj+oPwfUDWekOiPZo444vSWn0Z/wDqDe7KtXjTMMVpytWrLhfLrd/p0SWOCdFzDjnOiN1u1ja2naBWMqoqUuV9SrqraNDNTSzj4YjiGk0TSFKXOcQmYg6DlBVY9pvAUr0xqoDALVtXzTt3iviqrL/cr1P5QiRQRyYeeca98B2p+oYBk+1ndmfH0X1Ayfaz+zPj6L6gZPtZ/Znx9F9Qb/VdWAzFVjCqxjW8WeCmkVs290O7pU8uYlPSmaaI04wrzEhhcxxXPcXOQpCZVasrBZhu8V8VFnf7lep/J0qKCOTDzzTXvgO1P1Dqtl3bqzO9cKlF+iAsn2s/sz4+i+oK9qGZ9ZstVOo1MvaLdFlRp81DMNPhiTpDiaRpSkLonELmL7hoJBlVqLYS027cUiEAD1UAAAAGVWo9hLTbtxSIQAPVQBKtufqTv3lxlVlzbqzO9cKlF/jyrHIqqwyT+W24+YGqWpNhTTbtxSIQAAv+y5sJZneuKSjVQAAAAAAAAABldqPYU0268UiHn+PVQAAAEA2o9urTbtwqIZSNWsu7dWZ3rhUov4AAZXaj2EtNu3FIh5/j1UAAAAAAASrbn6k795cSqAC/7Lmwlmd64pKMrtzdSd98uMqsubdWZ3nhUov4BANqPbq027cKiGq2GOu24+YGqWo9hLTbrxSIQAAv+y5sJZneuKSjVQABAFqPbs0268KiGq2GOu24+YFVAAgG1Ft1abdeFRDVbDPXbcfMDVbUewppt14pEPP8eqgCVbc/UnfvLjK7Lm3Vmd64VKL+AQDaj26tNu3CohlI1ay7t1ZneuFSi/h5VjkcAA9VAAAABn1fLQLNlqqF4uVFSrosqNIlJpZp8MSdGYXROIUhdE4pM5OkSFlBVndpvAUX0wygqzu03gKL6YZQVZ3abwFF9MarUZ9u2G/ap9e4FkXD8LJnTJn3EET5SPnPdDmc8r9VyfasezPj6V6gZPtWPZnx9K9QMn2rHsz4+leoMArRrBaeqxulmxrBrPBTNq2VdaHd0SeXMRGJT9NKaceV56Q8uc4rnuJmIQgypuawWnbu5Y1LO/3KORydEigjhi5hpr3wG636h2qy5t1ZneuFSi/gEA2oturTbrwqEarYZ67bj5gapaj2EtNuvFIhAAv/ACfKsOzPj6V6g7+y7PqxlVFRVMoaLdVbRopSGYckhiOKcdpHFKUukcUucvSPrAAgDKDrP7TeAovpigKr6vmYrTYVWNk3iswq0iym3umXhKgmS0p6IzQRHGmEcYjMJmNI9zyvKUpRqjC1fMwwl+xVVlwvsF45QlSxwRQ8845zoztTtY7WAgDKDrP7TeAovpjoLUNAs2pXtJXK9pN6WNJhmppZqOKE0hhNE0hCEzGkJmIKUsM9dtx8wNVtR7Cmm3XikQ8/xquUHWf2m8BRfTFfVDNCs2oqoUa4XtKvaypM+amlmmRQp0hhuiaQhCaJpCZidAxa3NnxJ37y4yuy7t1ZneuFSi/hAGUHWf2m8BRfTFAVXVfsxWmwqsbJvFZhVpFlNvdMvCVBMlpT0RmgiONMI4xGYTMaR7nleUpSjKrVtXzMMJitiorLhfb1eOUJUscEmHnnHOdGdqdrHVbLu3Vmd64VKL+GVZPtWHZnx9K9QSFXyzysZatdeKdRUW6K2jSJSGYefDEgRnl0jilKXSOKXOUd/sp1fMw3eNGNSsv9yusjlCVFBHOi5hxr3wG636h36vmp9hWVqpXi5UKjuiyo0iUmvadJDEnRmF0TzykLonFJnJ0iQh6pAAAADqtaTI49sIs2bvtwvsrlEqbBAlMSc2I174HayaxgGSP32+E/ODJH77fCfnDAK0WRxEbpZs5fr/cpXKJUqONEYk5sRznRu1l1DtVRlbnsrw39SYVwlI/FyJcuZ+Q575nuc73jf6rrRWPbdKxnMV7hfZvKMITYIER6TmyjXvgdrJrG/iVsrjuT8W+SHsk9uv2jYbwFhr9X3S9SZP0H3kZkT5UXNI6J2dzyskfvt8J+cBKpPYV9o2G8O4F/V90us6d9B97GfC6bFzSvhdme8nGVz3J+LfJFVDAK0bOuPbdrNo8aLhfZXJ8HzYIERiPnTTXvgfqJrHVf0WO9WMu43a7fvI4rx7nQ9L83Va0rRWPbCLNm8V7hfZXKMITYIEpiTmyjXvgdrJrE/CqsrnuT8W+SGVx3J+LfJGq1F1ue1PDf1JgrBsj8XPmTJn5DXOl+97/cNVASrkjd9vhPzhyWtz2E/ZxgTDuBf1he7rOnfT/dQHwumw84r4X5nuJqlRdbntTw39SYKwbI/Fz5kyZ+Q1zpfve/3DVQHlWKAqus649sIrGjxouF9m8nwfNggSno+dNNe+B+omsdr/RY71Yy7jdrv+8jivHudD0vzdVrRtFY9sIs2cxXuF9lcowhNggSmJObKNe+B2smsT+KpyR++3wn5we1z2E/ZzgTDuBf1he7rOnfT/dQHwumw84r4X5nuJz+lP3Vxa3683n93BDd/e+Lodn7VVdZ1xEbpWNHjRf7lN5Pg+VHGiPR86ac50b9RdQ38Srkj99vhPzhyWtz2E/ZxgTDuBf1he7rOnfT/dQHwumw84r4X5nuI/Sn7q4tb9ebz+7ghu/vfF0Oz9qqus64iN0rGjxov9ym8nwfKjjRHo+dNOc6N+ouob+JWyuO5Pxb5IeyT26/aPhvAWGv1ddL1Jk/QfexmRPlRc0jonZ3PK/RY71Yy7jdrv8AvI4rx7nQ9L83VK0bRWPbCrNnMV7hfZXKMITYIEpiTmyjXvgdrJrGAD1TAAAAHyWoaBWMsoqSuF9SrqraNDNTSzkkMRxDTdE0hSlznEJmJ0joGUHVj2m8BSvTDKDqx7TeApXpjAa0av2nrTbpZtkwaswqzaylXSmXhEgmS0RiI/QSnGnkcejPJnNI9zyZikKXqmT9Wd2Z8fRfUHf6hqn27ZWtdRrlfKO6K2jT5qa9oEkMSBIYbomHlKXSOITMTpFfkEA5PtZ/Znx9F9Qb9VfWCzFVjCqxjW8WeCmkVs290OQlTy5iU9KZpojTjCvMSGFzHFc9xXFIUg1ZhawWYbu+4qLO/wBygn8nSooI4oeeaa98B2p+ofJr5Z9ZtTVOvFOoqNellSZEpDMNMihTozy6RxSEI400pc5RIOT7Wd2Z8fRfUFU5QdWHabwFK9MMoOrHtN4ClemMrrz+3bAnsq+vcCz7/wDhZM6XL+/gifKSc17oc7nkfizUVPN0yyhpS5Xyjuqto0E1Ne0B8MRxDCaJp5Sl0jiEzE6Rno1XJ8rP7M+PovqBk+VndmfH0X1BqtRf2E4b9qv1FhqRcPxU6TMmfcRwumo+c58WZ7iu1XKDqx7TeApXphlB1Y9pvAUr0wyg6se03gKV6YkGvpoFY1Na68XKhpN6VtJkSk0s4yKFAjMLonEIUmkaUmcg2qwz123HzApNqGgVjLKKkrhfUq6q2jQzU0s5JDEcQ03RNIUpc5xCZidI6BlB1Y9pvAUr0xAAv+y5sJZneuKSjqtq2r5p27xXxVVl/uV6n8oRIoI5MPPONe+A7U/UJsamp5umVUNKXK+UV1VtGgmpr2gPhiOIYTRNPKUukcQmYnSM9HqoJAr4qebpqq2F6uFCo70raTIlJr2gRxQoEZhdE48hSaRpSZyD61Rn2E4b9qv1FhqRcPxU6TMmfcRwumo+c58WZ7iu1TKDqx7TeApXphlB1YdpvAUr0wyg6sO03gKV6YkGvpoFY1Na68XKipN6VtJkSk0s4yKFAjMLonEIUmkaUmcnvGgWU6wWYYTGjGtZ3C+3WRydKljgnRcw05zozdbtY37KDqw7TeApXphlB1YdpvAUr0xK2T7Wd2Z8fRfUFe1DM+s2WqoUamXtGuiyo0+ahmGnwxJ0h5NI0pSF0TiFzFHQbVlXzTt3iviqrL/cr1P5QiRQRyYeeca98B2p+oYBk+1ndmfH0X1Ayfaz+zPj6L6gv4AAAAZVaj2EtNu3FIhAAC/7Lmwlmd64pKNVAAEA2o9urTbtwqIapYZ67bj5gVUQB5VgKrsM9dtx8wNUtSbCWm3XikQgAeqgCVbc3UnfvLiVQAckFVWGeu24+YGqWo9hLTbtxSIQAAv+y5sJZneuKSjVRlVqPYS027cUiEAj1TBwlW3MT+RO/eXEqgAAAD1UAAAAAAAAAAAAAAGVWo9hLTbrxSIQAAv+y5sJZneuKSjVRlVqTYS0268UiEAD1UEAWo9uzTbrwqIarYZ67bj5garai2EtNuvFIhAA9VBANqLbs0268KiGVj8gPVQQDaj26tNu3CohlIAAv+y5sJZneuKSjVRlVqPYS0268UiEAD1UABlVqPYS0268UiEAAL/subCWZ3rikoyu3N1J37y4lYB6pAAAADPq+WgWbLVULxcqKlXRZUaRKTSzUkMSdGYXROIUhdE4pM5OkSFlBVndpvAUX0wygqzu03gKL6YZQVZ3abwFF9MMoKs7tN4Ci+mGUFWd2m8BRfTDKCrO7TeAovphlBVndpvAUX0wygqzu03gKL6Y3+ylWC07d40Y1rO/3K6yOTokUEc6LmGmvfAbrfqHarUewlpt14pEIAAX/Zc2EszvXFJR1W1bWC07CYr4qrO4X29T+TokscEmHnmnOdGdqdrGV1XVgtPWm3SsY1vFnhVm1lNvdDkIkEyWiPSmaaI008jj0ZhcxxHucXMUpBv2T5Vh2Z8fSvUGqjPmpqeYVqV7SVyvVFellSYZqa9pzIoTSGE0TTyEJomkJmJ0DFq8vsKwJ7K/qLDU+/8A4qdJly/v44XTUnNc+LO9xHYu1FcLdNSoqSp18vb0rqTDNQ3NAZFCcQ8mkaYQpM5pC5i9Az0eqggG1Ft2abdeFRDtVlOr5mG7xoxqVl/uV1kcoSooI50XMONe+A3W/UN/yfKsOzPj6V6gZPlWHZnx9K9QStlBVndp/AUX0xv9V9XzMVpsKrGybxWYVaRZTb3TLwlQTJaU9EZoIjjTCOMRmEzGke55XlKUoyq1ZV8zDCYr4qqy4X29T+UJUscEmHnnHOdGdqdrHQahWeVbU1rqNTr6i3tXUmfNQzDzIoUCQ8mkaUhSOONNLmKK9yfasezPj6V6ggAX/Zc2EszvXFJRqoyq1HsJabdeKRCAB6qCQK+a4W6ZatdeKdQry6K2jSJSG6IEkMSBGeXSOMKUukcUucvSNAspVgtO3eNONSzv9yusjk6JFBHOi5hpr3wG636h2q1HsJabdeKRCAAGgstXE3TLKKjKdQry6q2jRSkN0QHwxHFPO0jjClLpHFLnL0jaqjPt2w37VPr3Asi4fhZM6ZM+4gifKR857ocznlf9Wvip9hGVqpXi5UKjuiyo0iUmvadJDEnRmF0TjykLonFJnJ0iQHD1TAAAAGVWo9hLTbtxSIQAKqyRu+3wn5wZI3fb4T84Mkbvt8J+cGSN32+E/ODJG77fCfnBkjd9vhPzhgNaLI4iN0s2cv1/uUrlEqVHGiMSc2I5zo3ay6hv9hnrtuPmBqlqPYS0268UiEACqskfvt8J+cN/quZHERhFYzl9v9ym8olSo40p6TmxHOdG7WXUOq151R+1PAn13grBs/8ACT5kyX+c1zpfve/3DKvZH7CvtGw3h3Av6vul1nTvoPvYz4XTYuaV8Lsz3k5yuO5Pxb5IqkHDK68qo/angT67wVg2f+EnzJkv85rnS/e9/uGAVoWdcRGFWbR40X+5SuT4PlRxpTEfOmnOdG/UXUJ/FVZXHcn4t8kPZJ7dftHw3gLDX6vul6kyfoPvIzInyouaR0Ts7nl1SoyqP2WYb+u8K4SkfhJEuXM/Oc98z3Od7xqoCVskfvt8J+cN/quZHERhVYzl9v8AcpvKJUqONKek5sRznRu1l1DALc/UnfvLjAKrmuxEbpWNHcr/AHKbyebKjjRHo+dCc50b9RdQ3/K47k/FvkjjJG77fCfnDn2uewr7OMCYdwL+sL3dZ076f7qA+F02HnFfC/M9xOMrjuT8W+SOrVo2ise2EWbOYr3C+yuUYQmwQJTEnNlGvfA7WTWJ/HqmMArRs649t0s2jxouF9lcnwfNggRGI+dNNe+B+omsdrqMqj9lmG/rvCuEpH4SRLlzPznPfM9zne8cWo9hLTbrxSIQAAoCq6zrj0wqsaPGi4X2byfB82CBKej500174H6iax2r9FnvVjLuN2u37yOK8e50PS/Nz7XPbr9nOBMBYa/WF7vUmT9P93AZE+VDziOifnc4rJH77fCfnCqQAAABlVqPYS027cUiEAD1UGfNTXCwrKr2kqZfLy6rKjQTUN0TnwxGkPJpGmFIXROIXMXpHy8oKrHtN4ClemGUFVj2m8BSvTDKCqx7TeApXpjVRANqPbq0268KiGqWGOu24+YG018s8s2pqoXinUVGvSypMiUhmGmRQp0Z5dI4pCEcaaUucokHJ8rO7M+PovqC/gHVm5rBZhhLljUs7hfY5HJ0qWOCGLmGnOdGbrdrGK18VwMK1VVC8UyiXl7WVJkSkN0To4oU6M47SOMIQmiaUucokAeqYz5p64WFZZe0pTL1e3VZUaCahuic+GI0083SNMKQuicQuYvSPrsLWCzDd33FRZ3+5QT+TpUUEcUPPNNe+A7U/UOqWo9hLTbrxSIQCQci/bLmwlmd64pKNVHyWoaBWMqoqSuF9SrqraNDNTSzkkMRxDTdE0hSlznEJmJ0joGUHVj2m8BSvTDKDqx7TeApXpjv7LNAq2pUVGXChpV6VtJilJpZyOKE4phdE4hCk0jSkzk6BNdufqTv3lxK4D1SEA2o9urTbtwqIZSOSDkX7lB1YdpvAUr0wyg6sO03gKV6YZQdWHabwFK9MZ/XxXAwrVVUL1TKFeXpZUmRKQ3ROZFCnRnnaRxhCE0TSlzl9wkAark+Vn9mfH0X1BX1QzPrNlqp1Gpl7RbosqNPmoZhp8MSdIcTSNKUhdE4hcxfcMWtzdSd+8uMrsu7dWZ3rhUov4AAAABlVqPYS027cUiEAD1UEA2o9urTbrwqIZSAD1UEAWo9urTbrwqIarYY67bj5gVUAAJWtz9Sd+8uJUAeqggG1Ft1abdeFRDVbDPXbcfMDVLUewlpt14pEIAAX/Zc2EszvXFJRqoyq1HsJabduKRCAAF/2XdhLMb1xSUZVbn6k795cSuPyPVQQBaj26tNu3CohlQAAAAD1UASrbmJ/InfvLjK7Lu3Vmd64VKL+AAAAAZVaj2EtNu3FIhAA9VBANqPbq0268KiGUgA9VBAFqPbq0268KiGq2GOu24+YFVAPKsX/Zc2EszvXFJRldubqTv3lxKgD1UAStbm6k795cZVZd26szvXCphfwCALUe3Vpt14VENVsMddtx8wKqAAAAAAAAABlVqPYS0268UiEAAAqqwx123HzA1W1HsJabduKRCAXj1TAAAAGVWo9hLTbtxSIQAPVQQDaj26tNuvCohlIAPVQQBaj26tNuvCohqthjrtuPmBtNfLQLNlqqF4uVFSrosqNIlJpZp8MSdGYXROIUhdE4pM5OkSFlBVndpvAUX0xlIv+y5sJZneuKSjK7c3UnfvLiVAGq5QdZ/abwFF9MV9UM0KzamqhRrhe0q9rKkz5qaWaZFCnSGG6JpCEJomkJmIMWtzF/kTv3lxldlzbqzO9cKmF/CAMoOs/tN4Ci+mKAqvq+ZitNhVY2TeKzCrSLKbe6ZeEqCZLSnojNBEcaYRxiMwmY0j3PK8pSlHVK8/sJwJ7K/qLDU+/wD4qdJly/v44XTUnNc+LO9xHZXlB1ndpvAUX0xxlB1ndpvAUX0wyg6zu03gKL6Yr6oZoVm1NVCjXC9pV7WVJnzU0s0yKFOkMN0TSEITRNITMToHQbVlYLTsJiviqs7hfb1P5OiSxwSYeeac50Z2p2sZ/UPXA3TVVrKNTL5eXpW0mfNQ3RAZFCgSHk0jDCFJpGkLmKK/EAZQdZ/abwFF9MV9UM0KzamqhRrhe0q9rKkz5qaWaZFCnSGG6JpCEJomkJmJ0DoNqysFp2ExXxVWdwvt6n8nRJY4JMPPNOc6M7U7WM/qHrgbpqq1lGpl8vL0raTPmobogMihQJDyaRhhCk0jSFzFFfiAMoOs/tN4Ci+mK+qGaFZtTVQo1wvaVe1lSZ81NLNMihTpDDdE0hCE0TSEzEGgjKrUewlpt14pEIAAWBUNU+wrU1UKNcr1R3tY0mfNTXtOZFCnSGE0TTyEJomkJmJ0D5Vef2E4E9lf1Fhqff8A8VOky5f38cLpqTmufFne4jsVaquFumqUVJUy+Xl7VtJhmobogMihOIeTSNMIUjjjSFzF6Bnw9VAAAABlVqPYS027cUiEAD1UGAVpWdce27WbR40XC+yuT4PmwQIjEfOmmvfA/UTWOqZI3fb4T84Mkbvt8J+cGSN32+E/OFVCAbUe3Zpt14VENUsMddtx8wKArSZHHthFmzd9uF9lcolTYIEpiTmxGvfA7WTWMAyR++3wn5w4yRu+3wn5we1v2FfZxgXDuBf1he7rOnfT/dQHwumw84r4X5nuJz+lP3Vxa3683n93BDd/e+Lodn6rWjZ1xEYVZtHjRf7lK5Pg+VHGlMR86ac50b9RdQn8ci/bLmwlmd64pKMrtzdSd+8uMAqua7ERulY0dyv9ym8nmyo40R6PnQnOdG/UXUN/yuO5Pxb5IlUb/VfaKxEYVWM5ivf7lN5RhCVHGlPSc2Uc50btZdQ7V+lP3Vxa3683n93BDd/e+Lodn6rWhZ1xEYVZtHjRf7lK5Pg+VHGlMR86ac50b9RdQwAci/bLmwlmd64pKMrtz9Sd+8uMpsubdWZ3rhUov8Srkjd9vhPzhz7XPYV9nOBMO4F/WF7us6d9P91AfC6bDzivhfme4j9Kburi1v15vP7uCG7+98XQ7Px7I/YU+sbDeHcC/q+6XWdO+g+8jPhdNi5pXwuzPeTnK47k/FvkjjJG77fCfnCgKrWRxEYVWM5fb/cpvKJUqONKek5sRznRu1l1DtQyq1HsJabdeKRCABVWSN32+E/OFAVXMjiIwqsZy/X+5TeUSpUcaU9JzYjnOjdrLqHVa86o/angT67wVg2f+EnzJkv85rnS/e9/uGVZI/fb4T84Mkfvt8J+cKpAAAAGVWo9hLTbtxSIQAL/AMoOrDtN4ClemGUHVh2m8BSvTDKDqw7TeApXphlB1YdpvAUr0wyg6sO03gKV6YZQdWHabwFK9MSFX00CsamtdeLlQ0m9K2kyJSaWcZFCgRmF0TiEKTSNKTOT3jaLDHXbcfMCqgAQDaj26tNu3CohqlhjrtuPmBqtqPYS0268UiEAAL/subCWZ3riko6ratq+adu8V8VVZf7lep/KESKCOTDzzjXvgO1P1DAMn2s/sz4+i+oGT7Wf2Z8fRfUDJ9rP7M+PovqBk+1ndmfH0X1Bv9lOr5p2ExoxrVlwvt1kcoRJY4J0XMOOc6M3W7WO/V8s+s2pqoXinUVGvSypMiUhmGmRQp0Z5dI8pCEcaaUucokLJ9rO7M+PovqDKxXtQ1cDCstVQo1Mvl5dFlRp81DdE6SGJOkPJnNMKQuicQuYvSOg2rKwWYbvFfFVZ3+5XqfydKigjkw880174DtT9Q6pZc26szvXCpRfwyrKDqw7TeApXpjAa0avmnrTbpZtkwaswqzaylXSmT0SCZLRGIj9BKcaeRx6M8mc0j3PI8hSFL2qoz7CcN+1T6iw1IuH4qdJmTPuI4XTUfOc+LM9xXfUr4rhYVqqqF4plEvL2saTIlIbonRxQp0Z5dI4whCaJpS5yiQh6pDPmprhYVlV7SVMvl5dVlRoJqG6Jz4YjSHk0jTCkLonELmL0j5eUFVj2m8BSvTHU60KwGYrTYVZsawazwq0iylXShyEqCZLSmJT9NKaaYRxiM8uc4j3OI8pSELgOT7Wd2Z8fRfUFVZQdWPabwFK9Md+ZZoFY1Kioy4UNKvatpMUpNLORxQnFMLonEIUmkaUmcnQPrj5LUNArGWUVJXC+pV1VtGhmppZySGI4hpuiaQpS5ziEzE6R0DKDqx7TeApXpjVQAAABlVqPYS027cUiEAAAAADkgqmwx123HzAqoAEAWo9urTbtwqIarYY67bj5garaj2EtNuvFIhAAC/7Lmwlmd64pKNVAAAAAeVo4HI1Sy5t1ZneuFSi/h5Vi/7Luwpmd54pKMqtz9Sd+8uJVAeqggC1Ht1abdeFRDKhqtlzbqzO88KlF/jyrF/2XNhLM71xSUaqMqtR7CWm3bikQgAeqgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/9k=";
    static  String  key = "0a62aa3d661cc934473b4bff633ec047";//固定值
    static String api_key ="20180903";
    public static String request(String ctnData, String mtnData, TreeMap<String, String> treemap , String img) {
        JSONObject params = new JSONObject();
        JSONObject command = new JSONObject();
        JSONObject parameters = new JSONObject();
        JSONArray objects = new JSONArray();
        long l = System.currentTimeMillis()/1000;
        String s=treemap.toString();

        Iterator titer=treemap.entrySet().iterator();
        String data="";
        while(titer.hasNext()){
            Map.Entry ent=(Map.Entry )titer.next();
            String keyt=ent.getKey().toString();
            String valuet=ent.getValue().toString();
            parameters.put(keyt,valuet);
            data+=(keyt+valuet);
        }
//        byte[] bytes = readStream(img);
//        parameters.put("head_img",base);
        parameters.put("headImg",img);
//        parameters.put("head_img",String.valueOf(readStream(img)));
        objects.add(parameters);
        params.put("ctn", ctnData);//控制器名
        params.put("mtn", mtnData);//方法名
        params.put("timestamp", l);//时间戳
        params.put("api_key",api_key);//固定时间
        params.put("data", objects);
        params.put("sign", postRequests(data,l));
        Log.e("request","请求最终结果="+params.toJSONString());
        return  params.toJSONString();
    }
    /** * 照片转byte二进制 * @param imagepath 需要转byte的照片路径 * @return 已经转成的byte * @throws Exception */
    public static byte[] readStream(String imagepath) throws Exception
    {FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer)))
        {
            outStream.write(buffer, 0, len);
        }
        outStream.close();fs.close();
        return outStream.toByteArray();
    }


    //    private static String postRequests(TreeMap<String, String> treemap)  {
//        String replace = treemap.toString().replace(" ", "");
//        String md5ResultCode =  MD5Utils.encryptMD5((MD5Utils.encryptMD5((replace)) + key));
//        return md5ResultCode;
//    }
    private static String postRequests(String replace,long time)  {
        String md5ResultCode =  MD5Utils.encryptMD5((MD5Utils.encryptMD5((replace)+time) + key)).toUpperCase();
        return md5ResultCode;
    }
}
