/* ParserHDDLTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. ParserHDDLTokenManager.java */
package com.kazimirm.transitionSystemBasedHtnSolver.parser;
import java.io.*;
import java.util.*;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class ParserHDDLTokenManager implements ParserHDDLConstants {

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x1000L) != 0L)
            return 0;
         if ((active0 & 0x1fffff8000L) != 0L)
         {
            jjmatchedKind = 37;
            return 0;
         }
         return -1;
      case 1:
         if ((active0 & 0x40020000L) != 0L)
            return 0;
         if ((active0 & 0x1fbffd8000L) != 0L)
         {
            if (jjmatchedPos != 1)
            {
               jjmatchedKind = 37;
               jjmatchedPos = 1;
            }
            return 0;
         }
         return -1;
      case 2:
         if ((active0 & 0x800018000L) != 0L)
            return 0;
         if ((active0 & 0x17fffc0000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 2;
            return 0;
         }
         return -1;
      case 3:
         if ((active0 & 0x1028000000L) != 0L)
            return 0;
         if ((active0 & 0x7d7fc0000L) != 0L)
         {
            if (jjmatchedPos != 3)
            {
               jjmatchedKind = 37;
               jjmatchedPos = 3;
            }
            return 0;
         }
         return -1;
      case 4:
         if ((active0 & 0x20400000L) != 0L)
            return 0;
         if ((active0 & 0x7d7bc0000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 4;
            return 0;
         }
         return -1;
      case 5:
         if ((active0 & 0x5800c0000L) != 0L)
            return 0;
         if ((active0 & 0x257b00000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 5;
            return 0;
         }
         return -1;
      case 6:
         if ((active0 & 0x4100000L) != 0L)
            return 0;
         if ((active0 & 0x253a00000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 6;
            return 0;
         }
         return -1;
      case 7:
         if ((active0 & 0x50000000L) != 0L)
            return 0;
         if ((active0 & 0x203a00000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 7;
            return 0;
         }
         return -1;
      case 8:
         if ((active0 & 0x800000L) != 0L)
            return 0;
         if ((active0 & 0x203200000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 8;
            return 0;
         }
         return -1;
      case 9:
         if ((active0 & 0x3000000L) != 0L)
            return 0;
         if ((active0 & 0x200200000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 9;
            return 0;
         }
         return -1;
      case 10:
         if ((active0 & 0x200200000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 10;
            return 0;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 5);
      case 41:
         return jjStopAtPos(0, 6);
      case 45:
         return jjStartNfaWithStates_0(0, 12, 0);
      case 58:
         return jjStopAtPos(0, 13);
      case 60:
         return jjStopAtPos(0, 9);
      case 61:
         return jjStopAtPos(0, 11);
      case 62:
         return jjStopAtPos(0, 10);
      case 63:
         return jjStopAtPos(0, 14);
      case 91:
         return jjStopAtPos(0, 7);
      case 93:
         return jjStopAtPos(0, 8);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x100008000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0xc0000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 104:
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x80000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x44020000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x203100000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x10000000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x28400000L);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x2a000000L);
      case 98:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000000L);
      case 99:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x80240000L);
      case 102:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000008000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x890000L);
      case 114:
         if ((active0 & 0x20000L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x241100000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000L);
      case 100:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(2, 15, 0);
         return jjMoveStringLiteralDfa3_0(active0, 0x40000000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x201000000L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x400040000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000000L);
      case 106:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000L);
      case 109:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 110:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(2, 35, 0);
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 113:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x28000000L);
      case 116:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(2, 16, 0);
         return jjMoveStringLiteralDfa3_0(active0, 0x180000000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x2080000L);
      case 98:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 100:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000L);
      case 101:
         return jjMoveStringLiteralDfa4_0(active0, 0x444400000L);
      case 104:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x100040000L);
      case 107:
         if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000L);
      case 116:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 36, 0);
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000000L);
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x404000000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x1280000L);
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000L);
      case 109:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x380000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000000L);
      case 115:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(4, 22, 0);
         else if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(4, 29, 0);
         break;
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 99:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000000L);
      case 100:
         if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(5, 31, 0);
         break;
      case 101:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(5, 18, 0);
         return jjMoveStringLiteralDfa6_0(active0, 0x2100000L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x40000000L);
      case 110:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(5, 19, 0);
         else if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(5, 32, 0);
         return jjMoveStringLiteralDfa6_0(active0, 0x200000000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x200000L);
      case 115:
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000L);
      case 116:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(5, 34, 0);
         return jjMoveStringLiteralDfa6_0(active0, 0x4000000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x1000000L);
      case 100:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000000L);
      case 101:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000L);
      case 107:
         return jjMoveStringLiteralDfa7_0(active0, 0x10000000L);
      case 109:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(6, 20, 0);
         break;
      case 110:
         return jjMoveStringLiteralDfa7_0(active0, 0x40800000L);
      case 115:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(6, 26, 0);
         break;
      case 116:
         return jjMoveStringLiteralDfa7_0(active0, 0x2000000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa8_0(active0, 0x2000000L);
      case 103:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(7, 30, 0);
         break;
      case 105:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000000L);
      case 109:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000L);
      case 115:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(7, 28, 0);
         break;
      case 116:
         return jjMoveStringLiteralDfa8_0(active0, 0x1800000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa9_0(active0, 0x1200000L);
      case 114:
         return jjMoveStringLiteralDfa9_0(active0, 0x2000000L);
      case 115:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(8, 23, 0);
         break;
      case 116:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjMoveStringLiteralDfa9_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000000L);
      case 110:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000L);
      case 115:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(9, 24, 0);
         else if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(9, 25, 0);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
static private int jjMoveStringLiteralDfa10_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 111:
         return jjMoveStringLiteralDfa11_0(active0, 0x200000000L);
      case 116:
         return jjMoveStringLiteralDfa11_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
static private int jjMoveStringLiteralDfa11_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(11, 33, 0);
         break;
      case 115:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(11, 21, 0);
         break;
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 1;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  kind = 37;
                  jjstateSet[jjnewStateCnt++] = 0;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  kind = 37;
                  jjstateSet[jjnewStateCnt++] = 0;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\50", "\51", "\133", "\135", "\74", "\76", "\75", 
"\55", "\72", "\77", "\141\156\144", "\156\157\164", "\157\162", 
"\144\145\146\151\156\145", "\144\157\155\141\151\156", "\160\162\157\142\154\145\155", 
"\162\145\161\165\151\162\145\155\145\156\164\163", "\164\171\160\145\163", "\143\157\156\163\164\141\156\164\163", 
"\160\162\145\144\151\143\141\164\145\163", "\160\141\162\141\155\145\164\145\162\163", "\157\142\152\145\143\164\163", 
"\164\141\163\153", "\163\165\142\164\141\163\153\163", "\164\141\163\153\163", 
"\157\162\144\145\162\151\156\147", "\155\145\164\150\157\144", "\141\143\164\151\157\156", 
"\160\162\145\143\157\156\144\151\164\151\157\156", "\145\146\146\145\143\164", "\150\164\156", "\151\156\151\164", null, };
static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {0
};

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

    /** Constructor. */
    public ParserHDDLTokenManager(SimpleCharStream stream){

      if (input_stream != null)
        throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);

    input_stream = stream;
  }

  /** Constructor. */
  public ParserHDDLTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  static public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  static private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 1; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  static public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public static void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x3fffffffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    static protected SimpleCharStream  input_stream;

    static private final int[] jjrounds = new int[1];
    static private final int[] jjstateSet = new int[2 * 1];
    private static final StringBuilder jjimage = new StringBuilder();
    private static StringBuilder image = jjimage;
    private static int jjimageLen;
    private static int lengthOfMatch;
    static protected int curChar;
}