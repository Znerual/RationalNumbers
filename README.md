# RationalNumbers
This project was made to enable calculations with fractions without the floatingpoint error

Construction:
The class BigRationalNumbers allows precission to arbitrary values. You can create an BigRationalNumber with an long or BigInteger n 
as argument in the constructor which creates an value n/1.
You can also set the denominator by using the two argument construction with either long or BigInteger.
The third option is to let the class try to create an fraction out of an decimal number. It uses the mathematical solution for evolving
countable rows by continuously adding smaller fractions and reducing the sum until the selected precision is reached.

Operations:
There are two kind of operations in the class:
  - The static operations take two values as input and do not change this values!
  - The class methods take just one or zero parameters (kuerzen means reducing)
  
Output:
You can access the value of the BigDecimalNumber only over the String output of the toString() method, which creates an value like this: n/m

